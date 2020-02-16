package com.stackingrule.passbook.vo;

import com.stackingrule.passbook.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <h1>优惠卷模板信息</h1>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplateInfo extends PassTemplate {

    /** 优惠卷模板 **/
    private PassTemplate passTemplate;

    /** 优惠卷对应商户 **/
    private Merchants merchants;

}
