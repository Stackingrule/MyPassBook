package com.stackingrule.passbook.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <h>商户对象模型</h>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchants")
public class Merchants {

    /* 商户id， 主键*/
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    /*商户名称*/
    @Basic
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /**商户logo*/
    @Basic
    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    /**商户营业执照*/
    @Basic
    @Column(name = "business_license", nullable = false)
    private String businessLicense;

    /* */
    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    /**/
    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    @Basic
    @Column(name = "is_audit", nullable = false)
    private Boolean isAudit = false;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAudit() {
        return isAudit;
    }

    public void setAudit(Boolean audit) {
        isAudit = audit;
    }
}
