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

@Log4j
@RestController
@RequestMapping("/data")
public class BlogController {

    @Autowired
    private BlogService blogService;

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

    @RequestMapping(value = "/blog/{uniqueId}", method = RequestMethod.GET)
    public IronResponseEntity getBlogDetail(@PathVariable("uniqueId") String uniqueId) {

        try {
            BlogDetailVO blogDetailVO = blogService.getBlogDetail(uniqueId);
            return new IronResponseEntity(ResponseStatus.SUCCESS, blogDetailVO);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }


}
