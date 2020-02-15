package com.stackingrule.passbook.controller;

import com.alibaba.fastjson.JSON;
import com.stackingrule.passbook.service.IMerchantsService;
import com.stackingrule.passbook.vo.CreateMerchantsRequest;
import com.stackingrule.passbook.vo.PassTemplate;
import com.stackingrule.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>商户服务controller</h1>
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantsController {

    /** 商户服务接口 **/
    private final IMerchantsService merchantsService;

    @Autowired
    public MerchantsController(IMerchantsService merchantsService) {
        this.merchantsService = merchantsService;
    }

    @PostMapping("/create")
    @ResponseBody
    public Response createMerchants(@RequestBody CreateMerchantsRequest request) {

        log.info("CreateMerchants: {}", JSON.toJSONString(request));
        return merchantsService.createMerchants(request);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public Response buildMerchantsInfo(@PathVariable Integer id) {

        log.info("BuildMerchantsInfo: {}", id);
        return merchantsService.buildMerchantsInfoById(id);
    }

    @PostMapping("/drop")
    @ResponseBody
    public Response dropPaddTemplate(@RequestBody PassTemplate passTemplate) {

        log.info("DropPaddTemplate: {}", passTemplate);
        return merchantsService.dropPassTemplate(passTemplate);
    }
}
