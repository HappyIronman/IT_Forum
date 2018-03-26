package com.ironman.forum.controller;

import com.ironman.forum.service.UserService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronResponseEntity;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.UserVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
