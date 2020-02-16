package com.stackingrule.passbook.service;

import com.stackingrule.passbook.vo.Pass;
import com.stackingrule.passbook.vo.Response;

/**
 * <h1>获取用户个人优惠卷信息</h1>
 */
public interface IUserPassService {

    /**
     * <h2>获取个人优惠卷信息，即我的优惠卷功能实现</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * @throws Exception
     */
    Response getUserPassInfo(Long userId) throws Exception;

    /**
     * <h2>获取用户已经消费的优惠卷, 即已使用的优惠卷功能实现</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * @throws Exception
     */
    Response getUserUsedPassInfo(Long userId) throws Exception;

    /**
     * <h2>获取用户所有的优惠卷</h2>
     * @param userid 用户 id
     * @return {@link Response}
     * @throws Exception
     */
    Response getUserAllPassInfo(Long userid) throws Exception;

    /**
     * <h2>用户使用优惠卷</h2>
     * @param pass {@link Pass}
     * @return {@link Response}
     */
    Response userUsePass(Pass pass);
}
