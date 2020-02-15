package com.stackingrule.passbook.service;


import com.stackingrule.passbook.vo.CreateMerchantsRequest;
import com.stackingrule.passbook.vo.PassTemplate;
import com.stackingrule.passbook.vo.Response;

/**
 * <h1>商户服务接口定义</h1>
 */

public interface IMerchantsService {

    /**
     * <h2>创建商户服务</h2>
     * @param request {@link CreateMerchantsRequest} 创建商户请求
     * @return {@link Response}
     */
    Response createMerchants(CreateMerchantsRequest request);

    /**
     * <h2>根据id构造商户信息</h2>
     * @param id 商户id
     * @return {@link Response}
     */
    Response buildMerchantsInfoById(Integer id);

    /**
     * <h2>投放优惠卷</h2>
     * @param passTemplate {@link PassTemplate} 优惠卷对象
     * @return {@link Response}
     */
    Response dropPassTemplate(PassTemplate passTemplate);


}
