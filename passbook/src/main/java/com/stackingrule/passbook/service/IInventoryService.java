package com.stackingrule.passbook.service;

import com.stackingrule.passbook.vo.Response;

/**
 * <h1>获取库存信息： 只返回用户没有领取的</h1>
 */
public interface IInventoryService {

    /**
     * <h2>获取库存信息</h2>
     * @param userId 用户 id
     * @return {@linl Response}
     * @throws Exception
     */
    Response getInventoryInfo(Long userId) throws Exception;
}
