package com.stackingrule.passbook.service;

import com.stackingrule.passbook.vo.GainPassTemplateRequest;
import com.stackingrule.passbook.vo.Response;

/**
 * <h1>用户领取优惠卷功能实现</h1>
 */
public interface IGainPassTemplateService {

    /**<h2>用户领取优惠卷</h2>
     * @param request {@link GainPassTemplateRequest}
     * @return {@link Response}
     * @throws Exception
     */
    Response gainPassTemplate(GainPassTemplateRequest request) throws Exception;
}
