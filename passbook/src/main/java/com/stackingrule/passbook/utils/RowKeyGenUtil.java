package com.stackingrule.passbook.utils;

import com.stackingrule.passbook.vo.Feedback;
import com.stackingrule.passbook.vo.PassTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <h1>RowKey 生成器工具类</h1>
 */
@Slf4j
public class RowKeyGenUtil {

    /**
     * <h2>根据提供的 PassTemplate 对象生成 RowKey </h2>
     * @param passTemplate {@link PassTemplate}
     * @return String RowKey
     */
    public static String genPassTemplateRowKey(PassTemplate passTemplate) {

        String passInfo = String.valueOf(passTemplate.getId() + "_" + passTemplate.getTitle());
        String rowKey = DigestUtils.md5Hex(passInfo);
        log.info("GenPassTemplateRowKey: {}, {}", passInfo, rowKey);

        return rowKey;
    }

    /**
     * <h2>根据提供的 Feedback 对象生成 RowKey </h2>
     * @param feedback {@link Feedback}
     * @return String RowKey
     */
    public static String genFeedbackRowKey(Feedback feedback) {

        return new StringBuilder(String.valueOf(feedback.getUserId())).reverse().toString() +
                (Long.MAX_VALUE - System.currentTimeMillis());
    }

}
