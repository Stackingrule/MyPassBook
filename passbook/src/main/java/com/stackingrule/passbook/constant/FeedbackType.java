package com.stackingrule.passbook.constant;

/**
 * <h1>评论类型枚举</h1>
 */
public enum FeedbackType {

    PASS(1, "针对优惠卷的评论"),
    APP(2, "针对卡包 App 的评论");

    /** 评论类型编码 **/
    private Integer code;

    /** 评论类型描述 **/
    private String desc;


    FeedbackType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
