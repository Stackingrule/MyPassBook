package com.stackingrule.passbook.service.impl;

import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import com.stackingrule.passbook.constant.Constants;
import com.stackingrule.passbook.constant.PassStatus;
import com.stackingrule.passbook.dao.MerchantsDao;
import com.stackingrule.passbook.entity.Merchants;
import com.stackingrule.passbook.mapper.PassRowMapper;
import com.stackingrule.passbook.service.IUserPassService;
import com.stackingrule.passbook.vo.Pass;
import com.stackingrule.passbook.vo.PassInfo;
import com.stackingrule.passbook.vo.PassTemplate;
import com.stackingrule.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h1>用户优惠卷相关功能实现</h1>
 */
@Slf4j
public class UserPassService implements IUserPassService {

    /** HBase 客户端 **/
    private HbaseTemplate hbaseTemplate;

    /** MerchantsDao **/
    private MerchantsDao merchantsDao;

    @Autowired
    public UserPassService(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
    }

    @Override
    public Response getUserPassInfo(Long userId) throws Exception {
        return null;
    }

    @Override
    public Response getUserUsedPassInfo(Long userId) throws Exception {
        return null;
    }

    @Override
    public Response getUserAllPassInfo(Long userid) throws Exception {
        return null;
    }

    @Override
    public Response userUsePass(Pass pass) {
        return null;
    }

    /**
     * <h1>根据优惠卷状态获取优惠卷信息</h1>
     * @param userId 用户 id
     * @param status {@link PassStatus}
     * @return {@link Response}
     * @throws Exception
     */
    private Response getPassInfoByStatus(Long userId, PassStatus status) throws Exception {

        // 根据 userId 构建行键前缀
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(userId)).reverse().toString());

        CompareFilter.CompareOp compareOp = status ==
                PassStatus.UNUSED ?
                CompareFilter.CompareOp.EQUAL : CompareFilter.CompareOp.NOT_EQUAL;
        Scan scan = new Scan();
        // 1. 行键前缀过滤器，找到特定用户优惠卷
        scan.setFilter(new PrefixFilter(rowPrefix));
        // 2. 基于列单元的过滤器， 找到未使用的优惠卷
        if (status != PassStatus.ALL) {
            scan.setFilter(new SingleColumnValueFilter(
                    Constants.PassTable.FAMILY_I.getBytes(),
                    Constants.PassTable.CON_DATE.getBytes(),
                    compareOp,
                    Bytes.toBytes("-1")));
        }

        List<Pass> passes = hbaseTemplate.find(Constants.PassTable.TABLE_NAME, scan, new PassRowMapper());
        Map<String, PassTemplate> passTemplateMap = buildPassTemplateMap(passes);
        Map<Integer, Merchants> merchantsMap = buildMerchantsMap(
                new ArrayList<>(passTemplateMap.values()));

        List<PassInfo> result = new ArrayList<>();

        for (Pass pass : passes) {
            PassTemplate passTemplate = passTemplateMap.getOrDefault(
                    pass.getTemplateId(), null);
            if (null == passTemplate) {
                log.error("PassTemplate Null : {}", pass.getTemplateId());
                continue;
            }

            Merchants merchants = merchantsMap.getOrDefault(
                    passTemplate.getId(), null);
            if (null == merchants) {
                log.error("Merchants Null : {}", passTemplate.getId());
                continue;
            }

            result.add(new PassInfo(pass, passTemplate, merchants));
        }

        return new Response(result);
    }
    /**
     * <h2>通过获取的Pass对象构造Map</h2>
     * @param passes {@link Pass}
     * @return Map {@link PassTemplate}
     * @throws Exception
     */
    private Map<String, PassTemplate> buildPassTemplateMap(List<Pass> passes) throws Exception {

        String[] patterns = new String[] {"yyyy-MM-dd"};

        byte[] FAMILY_B = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B);
        byte[] ID = Bytes.toBytes(Constants.PassTemplateTable.ID);
        byte[] TITLE = Bytes.toBytes(Constants.PassTemplateTable.TITLE);
        byte[] SUMMARY = Bytes.toBytes(Constants.PassTemplateTable.SUMMARY);
        byte[] DESC = Bytes.toBytes(Constants.PassTemplateTable.DESC);
        byte[] HAS_TOKEN = Bytes.toBytes(Constants.PassTemplateTable.HAS_TOKEN);
        byte[] BACKGROUND = Bytes.toBytes(Constants.PassTemplateTable.BACKGROUND);

        byte[] FAMILY_C = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C);
        byte[] LIMIT = Bytes.toBytes(Constants.PassTemplateTable.LIMIT);
        byte[] START = Bytes.toBytes(Constants.PassTemplateTable.START);
        byte[] END = Bytes.toBytes(Constants.PassTemplateTable.END);

        List<String> templateIds = passes.stream().map(
                Pass::getTemplateId
        ).collect(Collectors.toList());

        List<Get> templateGets = new ArrayList<>(templateIds.size());
        templateIds.forEach(t -> templateGets.add(new Get(Bytes.toBytes(t))));

        Result[] templateResults = hbaseTemplate.getConnection().
                getTable(TableName.valueOf(Constants.PassTemplateTable.TABLE_NAME))
                .get(templateGets);

        // 构造 PassTemplateId -> PassTemplate Object 的 Map,用于构造PassInfo
        Map<String, PassTemplate> templateId2Object = new HashMap<>();
        for (Result templateResult : templateResults) {
            PassTemplate passTemplate = new PassTemplate();

            passTemplate.setId(Bytes.toInt(templateResult.getValue(FAMILY_B, ID)));
            passTemplate.setTitle(Bytes.toString(templateResult.getValue(FAMILY_B, TITLE)));
            passTemplate.setSummary(Bytes.toString(templateResult.getValue(FAMILY_B, SUMMARY)));
            passTemplate.setDesc(Bytes.toString(templateResult.getValue(FAMILY_B, DESC)));
            passTemplate.setHasToken(Bytes.toBoolean(templateResult.getValue(FAMILY_B, HAS_TOKEN)));
            passTemplate.setBackground(Bytes.toInt(templateResult.getValue(FAMILY_B, BACKGROUND)));

            passTemplate.setLimit(Bytes.toLong(templateResult.getValue(FAMILY_C, LIMIT)));
            passTemplate.setStart(DateUtils.parseDate(
                    Bytes.toString(templateResult.getValue(FAMILY_C, START)), patterns));
            passTemplate.setEnd(DateUtils.parseDate(
                    Bytes.toString(templateResult.getValue(FAMILY_C, END)), patterns));

            templateId2Object.put(Bytes.toString(templateResult.getRow()), passTemplate);

        }

        return templateId2Object;
    }

    /**
     * <h2>通过获取的PassTemplate对象构造merchants Map</h2>
     * @param passTemplates {@link PassTemplate}
     * @return {@link Merchants}
     */
    private Map<Integer, Merchants> buildMerchantsMap(List<PassTemplate> passTemplates) {

        Map<Integer, Merchants> merchantsMap = new HashMap<>();
        List<Integer> merchantsIds = passTemplates.stream().map(
                PassTemplate::getId
        ).collect(Collectors.toList());
        List<Merchants> merchants = merchantsDao.findByInIn(merchantsIds);

        merchants.forEach(m -> merchantsMap.put(m.getId(), m));

        return merchantsMap;
    }
}
