package com.stackingrule.passbook.mapper;

import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import com.stackingrule.passbook.constant.Constans;
import com.stackingrule.passbook.vo.PassTemplate;
import com.stackingrule.passbook.vo.User;
import com.sun.xml.internal.ws.api.pipe.ServerTubeAssemblerContext;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import javax.swing.*;
import javax.xml.crypto.Data;

/**
 * <h1>HBase PassTemplate Row to User Object</h1>
 */
public class PassTemplateRowMapper implements RowMapper<PassTemplate> {

    private static byte[] FAMILY_B = Constans.PassTemplateTable.FAMILY_B.getBytes();
    private static byte[] ID = Constans.PassTemplateTable.ID.getBytes();
    private static byte[] TITLE = Constans.PassTemplateTable.TITLE.getBytes();
    private static byte[] SUMMARY = Constans.PassTemplateTable.SUMMARY.getBytes();
    private static byte[] DESC = Constans.PassTemplateTable.DESC.getBytes();
    private static byte[] HAS_TOKEN = Constans.PassTemplateTable.HAS_TOKEN.getBytes();
    private static byte[] BACKGROUND = Constans.PassTemplateTable.BACKGROUND.getBytes();

    private static byte[] FAMILY_C = Constans.PassTemplateTable.FAMILY_C.getBytes();
    private static byte[] LIMIT = Constans.PassTemplateTable.LIMIT.getBytes();
    private static byte[] START = Constans.PassTemplateTable.START.getBytes();
    private static byte[] END = Constans.PassTemplateTable.END.getBytes();

    @Override
    public PassTemplate mapRow(Result result, int rowNum) throws Exception {

        PassTemplate passTemplate = new PassTemplate();

        passTemplate.setId(Bytes.toInt(result.getValue(FAMILY_B, ID)));
        passTemplate.setTitle(Bytes.toString(result.getValue(FAMILY_B, TITLE)));
        passTemplate.setSummary(Bytes.toString(result.getValue(FAMILY_B, SUMMARY)));
        passTemplate.setDesc(Bytes.toString(result.getValue(FAMILY_B, DESC)));
        passTemplate.setHasToken(Bytes.toBoolean(result.getValue(FAMILY_B, HAS_TOKEN)));
        passTemplate.setBackground(Bytes.toInt(result.getValue(FAMILY_B, BACKGROUND)));

        String[] pattenrs = new String[] {"yyyy-MM-dd"};

        passTemplate.setLimit(Bytes.toLong(result.getValue(FAMILY_C, LIMIT)));
        passTemplate.setStart(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, START)), pattenrs));
        passTemplate.setEnd(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILY_C, END)), pattenrs));

        return passTemplate;
    }
}
