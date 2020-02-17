package com.stackingrule.passbook.controller;

import com.stackingrule.passbook.log.LogConstants;
import com.stackingrule.passbook.log.LogGenerator;
import com.stackingrule.passbook.service.IFeedbackService;
import com.stackingrule.passbook.service.IGainPassTemplateService;
import com.stackingrule.passbook.service.IInventoryService;
import com.stackingrule.passbook.service.IUserPassService;
import com.stackingrule.passbook.vo.Feedback;
import com.stackingrule.passbook.vo.GainPassTemplateRequest;
import com.stackingrule.passbook.vo.Pass;
import com.stackingrule.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>Passbook Rest Controller</h1>
 */
@Slf4j
@RestController
@RequestMapping("/passbook")
public class PassbookController {

    /** 用户优惠卷服务 **/
    private final IUserPassService userPassService;

    /** 优惠卷库存服务 **/
    private final IInventoryService inventoryService;

    /** 领取优惠卷服务 **/
    private final IGainPassTemplateService gainPassTemplateService;

    /** 反馈服务 **/
    private final IFeedbackService feedbackService;

    /** HttpServletRequest **/
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public PassbookController(IUserPassService userPassService,
                              IInventoryService inventoryService,
                              IGainPassTemplateService gainPassTemplateService,
                              IFeedbackService feedbackService,
                              HttpServletRequest httpServletRequest) {
        this.userPassService = userPassService;
        this.inventoryService = inventoryService;
        this.gainPassTemplateService = gainPassTemplateService;
        this.feedbackService = feedbackService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * <h2>获取用户个人优惠卷信息</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * @throws Exception
     */
    @GetMapping("/userpassinfo")
    @ResponseBody
    Response userPassInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.USER_PASS_INFO,
                null
        );

        return userPassService.getUserPassInfo(userId);
    }


    /**
     * <h2>获取用户使用的优惠卷信息</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * @throws Exception
     */
    @GetMapping("/userusedpassinfo")
    @ResponseBody
    Response userUsedPassInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.USER_USED_PASS_INFO,
                null
        );

        return userPassService.getUserUsedPassInfo(userId);
    }


    /**
     * <h2>用户使用优惠卷</h2>
     * @param pass {@link Pass}
     * @return {@link Response}
     */
    @ResponseBody
    @PostMapping("/userusepass")
    Response userUsePass(Pass pass) {

        LogGenerator.genLog(
                httpServletRequest,
                pass.getUserId(),
                LogConstants.ActionName.USER_USE_PASS,
                pass
        );

        return userPassService.userUsePass(pass);
    }


    /**
     * <h2>获取库存信息</h2>
     * @param userId 用户 id
     * @return {@link Response}
     * @throws Exception
     */
    @GetMapping("/inventoryinfo")
    @ResponseBody
    Response inventoryInfo(Long userId) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.INVENTORY_INFO,
                null
        );

        return inventoryService.getInventoryInfo(userId);
    }


    /**
     * <h2>用户领取优惠卷</h2>
     * @param request {@link GainPassTemplateRequest}
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/gainpasstemplate")
    @ResponseBody
    Response gainPassTemplate(@RequestBody GainPassTemplateRequest request)
            throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                request.getUserId(),
                LogConstants.ActionName.GAIN_PASS_TEMPLATE,
                request
        );

        return gainPassTemplateService.gainPassTemplate(request);
    }

    /**
     * <h2>用户创建评论</h2>
     * @param feedback {@link Feedback}
     * @return {@link Response}
     */
    @PostMapping("/createfeedback")
    @ResponseBody
    Response createFeedback(@RequestBody Feedback feedback) {

        LogGenerator.genLog(
                httpServletRequest,
                feedback.getUserId(),
                LogConstants.ActionName.CREATE_FEEDBACK,
                feedback
        );

        return feedbackService.createFeedback(feedback);
    }


    /**
     * <h2>用户获取评论信息</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    @GetMapping("/getfeedback")
    @ResponseBody
    Response getFeedback(Long userId) {

        LogGenerator.genLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.GET_FEEDBACK,
                null
        );

        return feedbackService.getFeedback(userId);
    }

    /**
     * <h2>异常演示接口</h2>
     * @return {@link Response}
     * @throws Exception
     */
    @GetMapping("exception")
    @ResponseBody
    Response exception() throws Exception {
        throw new Exception("Welcome To !");
    }
}
