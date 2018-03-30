package com.ironman.forum.controller;

import com.ironman.forum.form.UserLoginForm;
import com.ironman.forum.service.UserService;
import com.ironman.forum.util.*;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.BaseLogVO;
import com.ironman.forum.vo.FollowerVO;
import com.ironman.forum.vo.UserInfoVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/data")
@Log4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/my/info", method = RequestMethod.GET)
    public IronResponseEntity getMyInfo() {
        try {
            UserInfoVO userInfoVO = userService.getMyBaseInfo();
            return new IronResponseEntity(ResponseStatus.SUCCESS, userInfoVO);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    @RequestMapping(value = "my/aboutmes", method = RequestMethod.GET)
    public IronResponseEntity getAboutmeList(PageRequest pageRequest) {
        try {
            List<BaseLogVO> baseLogVOList = userService.pageAboutMeList(pageRequest);
            return new IronResponseEntity(ResponseStatus.SUCCESS, baseLogVOList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }


    /**
     * 获取我的粉丝
     *
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = "/my/followers", method = RequestMethod.GET)
    public IronResponseEntity getMyFollowerList(PageRequest pageRequest) {
        try {
            List<FollowerVO> followerList = userService.pageMyFollowerList(pageRequest);
            return new IronResponseEntity(ResponseStatus.SUCCESS, followerList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    /**
     * 获取我关注的人
     *
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = "/my/followings", method = RequestMethod.GET)
    public IronResponseEntity getMyFollowingList(PageRequest pageRequest) {
        try {
            List<FollowerVO> followingList = userService.pageMyFollowingList(pageRequest);
            return new IronResponseEntity(ResponseStatus.SUCCESS, followingList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public IronResponseEntity getUserBaseInfo(@PathVariable("userId") String uniqueId) {
        try {
            UserInfoVO userInfoVO = userService.getUserBaseInfo(uniqueId);
            return new IronResponseEntity(ResponseStatus.SUCCESS, userInfoVO);
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

    @RequestMapping(value = "/user/follow/{userId}", method = RequestMethod.POST)
    public IronResponseEntity userFollow(@PathVariable("userId") String userUniqueId) {
        try {
            int relation = userService.followUser(userUniqueId);
            return new IronResponseEntity(ResponseStatus.SUCCESS, relation);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }


}
