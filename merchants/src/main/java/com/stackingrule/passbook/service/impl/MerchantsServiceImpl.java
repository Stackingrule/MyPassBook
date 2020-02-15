package com.stackingrule.passbook.service.impl;


import com.stackingrule.passbook.constant.ErrorCode;
import com.stackingrule.passbook.dao.MerchantsDao;
import com.stackingrule.passbook.service.IMerchantsService;
import com.stackingrule.passbook.vo.CreateMerchantsRequest;
import com.stackingrule.passbook.vo.CreateMerchantsResponse;
import com.stackingrule.passbook.vo.PassTemplate;
import com.stackingrule.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <h1>商户服务接口实现</h1>
 */
@Slf4j
@Service
public class MerchantsServiceImpl implements IMerchantsService {

    /** Merchants 数据库接口 **/
    private final MerchantsDao merchantsDao;

    public MerchantsServiceImpl(MerchantsDao merchantsDao) {
        this.merchantsDao = merchantsDao;
    }


    @Override
    @Transactional
    public Response createMerchants(CreateMerchantsRequest request) {
        Response response = new Response();
        CreateMerchantsResponse merchantsResponse = new CreateMerchantsResponse();

        ErrorCode errorCode = request.validate(merchantsDao);
        if (errorCode != ErrorCode.SUCCESS) {
            merchantsResponse.setId(-1);
            response.setErrorCode(errorCode.getCode());
            response.setErrorMsg(errorCode.getDesc());
        } else {
            merchantsResponse.setId(merchantsDao.save(request.toMerchants()).getId());
        }
        response.setData(merchantsResponse);

        return response;
    }

    @Override
    public Response buildMerchantsInfoById(Integer id) {
        return null;
    }

    @Override
    public Response dropPassTemplate(PassTemplate passTemplate) {
        return null;
    }
}
