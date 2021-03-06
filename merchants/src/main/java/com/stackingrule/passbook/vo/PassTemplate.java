package com.stackingrule.passbook.vo;

import com.stackingrule.passbook.constant.ErrorCode;
import com.stackingrule.passbook.dao.MerchantsDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h>投放的优惠卷的对象定义</h>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplate {

    /* 所属用户 id */
    private Integer id;

    /* 优惠卷标题 */
    private String title;

    /* 优惠卷摘要 */
    private String summary;

    /* 优惠卷详细信息 */
    private String desc;

    /* 最大数限制 */
    private Long limit;

    /* 优惠卷是否有 Token，用于商户核销 */
    private Boolean hasToken;   //token存储与redis中

    /* 优惠卷背景色 */
    private Integer background;

    /* 优惠卷开始时间 */
    private Date start;

    /* 优惠卷结束时间 */
    private Date end;

    /**
     * <h2>检验优惠卷对象的有效性</h2>
     * @param merchantsDao
     * @return {@link ErrorCode}
     */
    public ErrorCode validate(MerchantsDao merchantsDao) {

        if (null == merchantsDao.findById(id)) {
            return ErrorCode.MERCHANTS_NOT_EXIST;
        }

        return ErrorCode.SUCCESS;
    }
}
