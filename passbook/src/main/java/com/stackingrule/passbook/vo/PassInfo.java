package com.stackingrule.passbook.vo;

import com.stackingrule.passbook.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>用户领取的优惠卷信息</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassInfo {

    /** 优惠卷信息 **/
    private Pass pass;

    /** 优惠卷模板 **/
    private PassTemplate passTemplate;

    /** 优惠卷对应商户信息 **/
    private Merchants merchants;

}
