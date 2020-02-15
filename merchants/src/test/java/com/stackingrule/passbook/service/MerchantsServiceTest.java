package com.stackingrule.passbook.service;

import com.alibaba.fastjson.JSON;
import com.stackingrule.passbook.vo.CreateMerchantsRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>商户服务测试</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MerchantsServiceTest {

    @Autowired
    private IMerchantsService merchantsService;

    @Test
    @Transactional
    public void TestCreateMerchantServ() {
        CreateMerchantsRequest request = new CreateMerchantsRequest();
        request.setName("慕课");
        request.setLogoUrl("www.imooc.com");
        request.setBusinessLicenseUrl("www.imooc.com");
        request.setPhone("1234567890");
        request.setAddress("北京市");

        System.out.println(JSON.toJSONString(merchantsService.createMerchants(request)));
    }

}
