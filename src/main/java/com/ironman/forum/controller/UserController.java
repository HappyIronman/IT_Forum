package com.ironman.forum.controller;

import com.ironman.forum.form.LikeArticleFormBean;
import com.ironman.forum.form.RegisterForm;
import com.ironman.forum.form.UserEditForm;
import com.ironman.forum.form.UserLoginForm;
import com.ironman.forum.service.UserService;
import com.ironman.forum.util.*;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.BaseLogVO;
import com.ironman.forum.vo.FollowerVO;
import com.ironman.forum.vo.UserInfoVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户相关控制器
 */
@RestController
@RequestMapping(value = "/data")
@Log4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取登录用户个人信息
     *
     * @return
     */
    @RequestMapping(value = "/my/info", method = RequestMethod.GET)
    public IronResponseEntity getMyInfo() throws GlobalException {
        UserInfoVO userInfoVO = userService.getMyBaseInfo();
        return new IronResponseEntity(ResponseStatus.SUCCESS, userInfoVO);
    }

    /**
     * 编辑个人信息
     *
     * @param form
     * @param result
     * @param session
     * @return
     */
    @RequestMapping(value = "/my/edit", method = RequestMethod.PUT)
    public ResponseEntity editInfo(@RequestBody @Valid UserEditForm form, BindingResult result, HttpSession session)
            throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        UserInfoVO userInfoVO = userService.editInfo(form, session);
        return new IronResponseEntity(ResponseStatus.SUCCESS, userInfoVO);
    }


    /**
     * 分页获取“关于我”列表
     *
     * @param pageRequest
     * @param result
     * @return
     */
    @RequestMapping(value = "my/aboutmes", method = RequestMethod.GET)
    public IronResponseEntity getAboutmeList(@Valid PageRequest pageRequest, BindingResult result)
            throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        List<BaseLogVO> baseLogVOList = userService.pageAboutMeList(pageRequest);
        return new IronResponseEntity(ResponseStatus.SUCCESS, baseLogVOList);
    }


    /**
     * 分页获取我的粉丝列表
     *
     * @param pageRequest
     * @param result
     * @return
     */
    @RequestMapping(value = "/my/followers", method = RequestMethod.GET)
    public IronResponseEntity getMyFollowerList(@Valid PageRequest pageRequest, BindingResult result) throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        List<FollowerVO> followerList = userService.pageMyFollowerList(pageRequest);
        return new IronResponseEntity(ResponseStatus.SUCCESS, followerList);
    }


    /**
     * 分页获取我关注的人列表
     *
     * @param pageRequest
     * @param result
     * @return
     */
    @RequestMapping(value = "/my/followings", method = RequestMethod.GET)
    public IronResponseEntity getMyFollowingList(@Valid PageRequest pageRequest, BindingResult result) throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        List<FollowerVO> followingList = userService.pageMyFollowingList(pageRequest);
        return new IronResponseEntity(ResponseStatus.SUCCESS, followingList);

    }

    /**
     * 获取首页“关于我”新消息数目
     *
     * @return
     */
    @RequestMapping(value = "/my/new_about_me_num", method = RequestMethod.GET)
    public IronResponseEntity getNewAboutMeNum() {
        int num = userService.getNewAboutMeNum();
        return new IronResponseEntity(ResponseStatus.SUCCESS, num);
    }

    /**
     * 获取用户基本信息
     *
     * @param uniqueId
     * @return
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public IronResponseEntity getUserBaseInfo(@PathVariable("userId") String uniqueId) throws GlobalException {
        UserInfoVO userInfoVO = userService.getUserBaseInfo(uniqueId);
        return new IronResponseEntity(ResponseStatus.SUCCESS, userInfoVO);
    }

    /**
     * 用户登录接口
     *
     * @param form
     * @param result
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public IronResponseEntity userLogin(@RequestBody @Valid UserLoginForm form, BindingResult result, HttpSession session)
            throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        UserInfoVO userInfoVO = userService.userLogin(form, session);
        return new IronResponseEntity(ResponseStatus.SUCCESS, userInfoVO);
    }

    /**
     * 关注某人接口
     *
     * @param userUniqueId
     * @return
     */
    @RequestMapping(value = "/my/follow/{userId}", method = RequestMethod.POST)
    public IronResponseEntity userFollow(@PathVariable("userId") String userUniqueId) throws GlobalException {
        int relation = userService.followUser(userUniqueId);
        return new IronResponseEntity(ResponseStatus.SUCCESS, relation);
    }


    /**
     * 检查手机号是否重复接口
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "/user/checkPhone", method = RequestMethod.POST)
    public IronResponseEntity checkPhone(String phone) throws GlobalException {
        userService.checkPhone(phone);
        return new IronResponseEntity(ResponseStatus.SUCCESS);
    }

    /**
     * 检查用户名是否重复接口
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/user/checkUsername", method = RequestMethod.POST)
    public IronResponseEntity checkUsername(String username) throws GlobalException {
        userService.checkUsername(username);
        return new IronResponseEntity(ResponseStatus.SUCCESS);
    }

    /**
     * 用户注册接口
     *
     * @param form
     * @param result
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public IronResponseEntity register(@RequestBody @Valid RegisterForm form, BindingResult result, HttpSession session) throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        UserInfoVO userInfoVO = userService.register(form, session);
        return new IronResponseEntity(ResponseStatus.SUCCESS, userInfoVO);
    }

    /**
     * 用户登出接口
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public IronResponseEntity logout(HttpSession session) {
        userService.logout(session);
        return new IronResponseEntity(ResponseStatus.SUCCESS);
    }



    /**
     * 给文章点赞
     *
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/article/like", method = RequestMethod.POST)
    public IronResponseEntity likeArticle(@RequestBody @Valid LikeArticleFormBean form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            userService.likeArticle(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    /**
     * 取消赞
     *
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/article/cancel_like", method = RequestMethod.POST)
    public IronResponseEntity cancelLikeArticle(@RequestBody @Valid LikeArticleFormBean form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            userService.cancelLikeArticle(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }
}
