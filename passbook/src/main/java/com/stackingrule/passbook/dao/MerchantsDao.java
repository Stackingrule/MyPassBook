package com.stackingrule.passbook.dao;

import com.stackingrule.passbook.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * <h1>Merchants Dao 接口</h1>
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {

    /**
     * <h1>通过 id 获取商户对象</h1>
     * @param id 商户 id
     * @return {@link Merchants}
     */
    Optional<Merchants> findById(Integer id);

    /**
     * <h2>根据商户名称获取商户对象</h2>
     * @param name 商户 name
     * @return {@link Merchants}
     */
    Merchants findByName(String name);

    /**
     *
     * <h2>根据商户 ids 获取商户对象</h2>
     * @param ids 商户 ids
     * @return {@link Merchants}
     */
    List<Merchants> findByIdIn(List<Integer> ids);

}
