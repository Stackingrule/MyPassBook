package com.stackingrule.passbook.vo;

import com.stackingrule.passbook.constant.ErrorCode;
import com.stackingrule.passbook.dao.MerchantsDao;
import com.stackingrule.passbook.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>创建商户请求对象</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsRequest {

    /* 商户名称 */
    private String name;

    /* 商户logo */
    private String logoUrl;

    /* 商户营业执照 */
    private String businessLicenseUrl;

    /* 商户电话 */
    private String phone;

    /* 商户地址 */
    private String address;

    /**
     * <h2>验证请求有效性</h2>
     * @param merchantsDao
     * @return
     */
    public ErrorCode validate(MerchantsDao merchantsDao) {

        if (merchantsDao.findByName(this.name) != null) {
            return ErrorCode.DUPLICATE_NAME;
        }

        if (null == this.logoUrl) {
            return ErrorCode.EMPTY_LOGO;
        }

        if (null == this.businessLicenseUrl) {
            return ErrorCode.EMPTY_BUSINESS_LICENSE;
        }

        if (null == this.address) {
            return ErrorCode.EMPTY_ADDRESS;
        }

        if (null == this.phone) {
            return ErrorCode.ERROR_PHONE;
        }

        return ErrorCode.SUCCESS;
    }


    /**
     * <h2>将请求对象转换为商户对象</h2>
     * @return
     */
    public Merchants toMerchants() {

        Merchants merchants = new Merchants();

        merchants.setName(name);
        merchants.setLogoUrl(logoUrl);
        merchants.setBusinessLicense(businessLicenseUrl);
        merchants.setPhone(phone);
        merchants.setAddress(address);

        return merchants;
    }

}
