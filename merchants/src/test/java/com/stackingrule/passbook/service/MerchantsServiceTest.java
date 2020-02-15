package com.stackingrule.passbook.service;

import com.alibaba.fastjson.JSON;
import com.stackingrule.passbook.vo.CreateMerchantsRequest;
import com.stackingrule.passbook.vo.PassTemplate;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * <h1>商户服务测试</h1>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MerchantsServiceTest {

    @Autowired
    private IMerchantsService merchantsService;

    @Test
    //@Transactional
    public void TestCreateMerchantServ() {
        CreateMerchantsRequest request = new CreateMerchantsRequest();
        request.setName("慕课");
        request.setLogoUrl("www.imooc.com");
        request.setBusinessLicenseUrl("www.imooc.com");
        request.setPhone("1234567890");
        request.setAddress("北京市");

        System.out.println(JSON.toJSONString(merchantsService.createMerchants(request)));
    }

    /**
     * {"data":{"address":"北京市","businessLicenseUrl":"www.imooc.com",
     * "id":3,"isAudit":false,"logoUrl":"www.imooc.com",
     * "name":"慕课","phone":"1234567890"},"errorCode":0,"errorMsg":""}
     */
    @Test
    public void testBuildMerchantsInfoById() {
        System.out.println(JSON.toJSONString(merchantsService.buildMerchantsInfoById(3)));
    }


    /**
     * DropPassTemplate: {"background":2,"desc":"详情： 慕课","end":1582619891597,
     * "hasToken":false,"id":3,"limit":10000,"start":1581755891597,"summary":"简介： 慕课","title":"title: 慕课"}
     * {"errorCode":0,"errorMsg":""}
     */
    @Test
    public void testDropPassTemplate() {

        PassTemplate passTemplate = new PassTemplate();
        passTemplate.setId(3);
        passTemplate.setTitle("title: 慕课");
        passTemplate.setSummary("简介： 慕课");
        passTemplate.setDesc("详情： 慕课");
        passTemplate.setLimit(10000L);
        passTemplate.setHasToken(false);
        passTemplate.setBackground(2);
        passTemplate.setStart(new Date());
        passTemplate.setEnd(DateUtils.addDays(new Date(), 10));

        System.out.println(JSON.toJSONString(
                merchantsService.dropPassTemplate(passTemplate)
        ));

    }

}
