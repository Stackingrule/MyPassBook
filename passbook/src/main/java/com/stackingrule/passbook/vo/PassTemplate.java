package com.stackingrule.passbook.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>投放的优惠卷对象定义</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplate {

    /** 所属商户 id **/
    private Integer id;

    /** 优惠卷标题 **/
    private String title;

    /*** 优惠卷摘要 **/
    private String summary;

    /** 优惠卷详细信息 **/
    private String desc;

    /** 最大个数限制 **/
    private Long limit;

    /** 优惠卷是否有 token ，用于商户核销**/
    private Boolean hasToken;

    /** 优惠卷背景色 **/
    private Integer background;

    /** 优惠卷开始时间 **/
    private Date start;

    /** 优惠卷结束时间 **/
    private Date end;
}
