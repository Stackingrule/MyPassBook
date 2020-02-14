package com.stackingrule.passbook.dao;

import com.stackingrule.passbook.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <h></h>
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {

    Optional<Merchants> findById(Integer id);

    Merchants findByName(String name);


}
