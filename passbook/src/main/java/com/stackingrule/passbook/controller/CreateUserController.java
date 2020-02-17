package com.stackingrule.passbook.controller;

import com.stackingrule.passbook.log.LogConstants;
import com.stackingrule.passbook.log.LogGenerator;
import com.stackingrule.passbook.service.IUserPassService;
import com.stackingrule.passbook.service.IUserService;
import com.stackingrule.passbook.vo.Response;
import com.stackingrule.passbook.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>创建用户服务</h1>
 */
@Slf4j
@Controller
@RequestMapping("/passbook")
public class CreateUserController {

    /** 创建用户服务 **/
    private final IUserService userService;

    /** HttpServletRequest **/
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public CreateUserController(IUserService userService, HttpServletRequest httpServletRequest) {
        this.userService = userService;
        this.httpServletRequest = httpServletRequest;
    }


    /**
     * <h2>创建用户</h2>
     * @param user {@link User}
     * @return {@link Response}
     * @throws Exception
     */
    @PostMapping("/createuser")
    @ResponseBody
    Response createUser(@RequestBody User user) throws Exception {

        LogGenerator.genLog(
                httpServletRequest,
                (long) -1,
                LogConstants.ActionName.CREATE_USER,
                user
        );

        return userService.createUser(user);
    }

}
