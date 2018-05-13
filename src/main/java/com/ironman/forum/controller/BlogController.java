package com.ironman.forum.controller;

import com.ironman.forum.form.BlogPublishForm;
import com.ironman.forum.service.BlogService;
import com.ironman.forum.util.*;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.BlogAbsVO;
import com.ironman.forum.vo.BlogDetailVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 博客相关控制器
 */
@Log4j
@RestController
@RequestMapping("/data")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 发表博客
     *
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/blog", method = RequestMethod.POST)
    public IronResponseEntity publishBlog(@RequestBody @Valid BlogPublishForm form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            String uniqueId = blogService.publishBlog(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS, (Object) uniqueId);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    /**
     * 分页获取个人博客列表
     *
     * @param pageRequest
     * @param result
     * @return
     */
    @RequestMapping(value = "/myblogs", method = RequestMethod.GET)
    public IronResponseEntity pageMyBlogs(@Valid PageRequest pageRequest, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            List<BlogAbsVO> blogAbsVOList = blogService.pageMyBlogs(pageRequest);
            return new IronResponseEntity(ResponseStatus.SUCCESS, blogAbsVOList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    /**
     * 分页获取用户博客列表
     *
     * @param userUniqueId
     * @param pageRequest
     * @param result
     * @return
     */
    @RequestMapping(value = "/{userId}/blogs", method = RequestMethod.GET)
    public IronResponseEntity pageUserBlogs(@PathVariable("userId") String userUniqueId,
                                            @Valid PageRequest pageRequest, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            List<BlogAbsVO> blogAbsVOList = blogService.pageUserBlogs(userUniqueId, pageRequest);
            return new IronResponseEntity(ResponseStatus.SUCCESS, blogAbsVOList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    /**
     * 获取用户博客内容详情
     *
     * @param uniqueId
     * @return
     */
    @RequestMapping(value = "/blog/{uniqueId}", method = RequestMethod.GET)
    public IronResponseEntity getUserBlogDetail(@PathVariable("uniqueId") String uniqueId) {

        try {
            BlogDetailVO blogDetailVO = blogService.getUserBlogDetail(uniqueId);
            return new IronResponseEntity(ResponseStatus.SUCCESS, blogDetailVO);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }


    /**
     * 获取我的博客内容详情
     *
     * @param uniqueId
     * @return
     */
    @RequestMapping(value = "/my_blog/{uniqueId}", method = RequestMethod.GET)
    public IronResponseEntity getMyBlogDetail(@PathVariable("uniqueId") String uniqueId) throws GlobalException {

        BlogDetailVO blogDetailVO = blogService.getMyBlogDetail(uniqueId);
        return new IronResponseEntity(ResponseStatus.SUCCESS, blogDetailVO);
    }


}
