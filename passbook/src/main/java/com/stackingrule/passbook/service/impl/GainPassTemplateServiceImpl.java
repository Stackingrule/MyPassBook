package com.stackingrule.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.mysql.cj.exceptions.ClosedOnExpiredPasswordException;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import com.stackingrule.passbook.constant.Constants;
import com.stackingrule.passbook.mapper.PassTemplateRowMapper;
import com.stackingrule.passbook.service.IGainPassTemplateService;
import com.stackingrule.passbook.utils.RowKeyGenUtil;
import com.stackingrule.passbook.vo.GainPassTemplateRequest;
import com.stackingrule.passbook.vo.PassTemplate;
import com.stackingrule.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <h1>用户领取优惠卷功能实现</h1>
 */
@Slf4j
@Service
public class GainPassTemplateServiceImpl implements IGainPassTemplateService {

    /** HBase 客户端 **/
    private final HbaseTemplate hbaseTemplate;

    /** Redis 客户端 **/
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public GainPassTemplateServiceImpl(HbaseTemplate hbaseTemplate,
                                       StringRedisTemplate redisTemplate) {
        this.hbaseTemplate = hbaseTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Response gainPassTemplate(GainPassTemplateRequest request) throws Exception {

        PassTemplate passTemplate;
        String passTemplateId = RowKeyGenUtil.genPassTemplateRowKey(request.getPassTemplate());

        try {
            passTemplate = hbaseTemplate.get(
                    Constants.PassTable.TABLE_NAME,
                    passTemplateId,
                    new PassTemplateRowMapper()
            );
        } catch (Exception e) {
            log.error("Gain PassTemplate Error: {}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("Gain PassTemplate Error!");
        }

        if (passTemplate.getLimit() <= 1 && passTemplate.getLimit() != -1) {
            log.error("PassTemplate Limit Max : {}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("PassTemplate Limit Max!");
        }

        Date cur = new Date();

        if (!(cur.getTime() >= passTemplate.getStart().getTime()
                && cur.getTime() < passTemplate.getEnd().getTime())) {
            log.error("PassTemplate ValidTime Error: {}", JSON.toJSONString(request.getPassTemplate()));
            return Response.failure("PassTemplate ValidTime Error!");
        }

        // 减去优惠卷 limit
        if (passTemplate.getLimit() != -1) {
            List<Mutation> datas = new ArrayList<>();
            byte[] FAMILY_C = Constants.PassTemplateTable.FAMILY_C.getBytes();
            byte[] LIMIT = Constants.PassTemplateTable.LIMIT.getBytes();

            Put put = new Put(
                    Bytes.toBytes(passTemplateId)
            );
            put.addColumn(FAMILY_C, LIMIT,
                    Bytes.toBytes(passTemplate.getLimit() - 1));

            datas.add(put);

            hbaseTemplate.saveOrUpdate(Constants.PassTemplateTable.TABLE_NAME, (Mutation) datas);
        }

        // 将优惠卷保存到用户优惠卷表
        if (!addPassForUser(request, passTemplate.getId(), passTemplateId)) {
            return Response.failure("GainPassTemplate Failure!");
        }

        return Response.success();
    }

    /**
     * <h2>给用户添加优惠卷</h2>
     * @param request {@link GainPassTemplateRequest}
     * @param merchantsId 商户 id
     * @param passTemplateId 优惠卷 id
     * @return true/false
     */
    private boolean addPassForUser(GainPassTemplateRequest request,
                                   Integer merchantsId, String passTemplateId) throws Exception{

        byte[] FAMILY_I = Constants.PassTable.FAMILY_I.getBytes();
        byte[] USER_ID = Constants.PassTable.USER_ID.getBytes();
        byte[] TEMPLATE_ID = Constants.PassTable.TEMPLATE_ID.getBytes();
        byte[] TOKEN = Constants.PassTable.TOKEN.getBytes();
        byte[] ASSIGNED_DATE = Constants.PassTable.ASSIGNED_DATE.getBytes();
        byte[] CON_DATE = Constants.PassTable.CON_DATE.getBytes();

        List<Mutation> datas = new ArrayList<>();
        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genPassRowKey(request)));
        put.addColumn(FAMILY_I, USER_ID, Bytes.toBytes(request.getUserId()));
        put.addColumn(FAMILY_I, TEMPLATE_ID, Bytes.toBytes(passTemplateId));

        if (request.getPassTemplate().getHasToken()) {
            String token = redisTemplate.opsForSet().pop(passTemplateId);
            if (null == token) {
                log.error("Token Not Exist: {}", passTemplateId);
                return false;
            }
            recordTokenToFile(merchantsId, passTemplateId, token);
            put.addColumn(FAMILY_I, TOKEN, Bytes.toBytes(token));
        } else {
            put.addColumn(FAMILY_I, TOKEN, Bytes.toBytes("-1"));
        }

        put.addColumn(FAMILY_I, ASSIGNED_DATE,
                Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())));
        put.addColumn(FAMILY_I, CON_DATE, Bytes.toBytes("-1"));

        datas.add(put);
        hbaseTemplate.saveOrUpdate(Constants.PassTable.TABLE_NAME, (Mutation) datas);

        return true;
    }

    /**
     * <h2>将已经使用的token记录到文件中</h2>
     * @param merchantsId 用户 id
     * @param passTemplateId 优惠卷 id
     * @Param token 分配的优惠卷 token
     */
    private void recordTokenToFile(Integer merchantsId, String passTemplateId,
                                   String token) throws IOException {

        Files.write(
                Paths.get(Constants.TOKEN_DIR, String.valueOf(merchantsId) +
                        passTemplateId + Constants.USED_TOKEN_SUFFIX),
                (token + "\n").getBytes(),
                StandardOpenOption.APPEND
        );
    }
}
