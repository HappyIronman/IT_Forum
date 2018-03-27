package com.ironman.forum.controller;

import com.ironman.forum.form.UserLoginForm;
import com.ironman.forum.service.UserService;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.UserVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/data")
@Log4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/mine", method = RequestMethod.GET)
    public IronResponseEntity getMineInfo() {
        try {
            UserVO userVO = userService.getUserBaseInfo();
            return new IronResponseEntity(ResponseStatus.SUCCESS, userVO);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public IronResponseEntity userLogin(@RequestBody @Valid UserLoginForm form, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            userService.userLogin(form, session);
            return new IronResponseEntity(ResponseStatus.SUCCESS);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }
}
