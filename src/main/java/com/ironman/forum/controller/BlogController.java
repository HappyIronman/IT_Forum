package com.ironman.forum.controller;

import com.ironman.forum.form.BlogPublishForm;
import com.ironman.forum.service.BlogService;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.BlogVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/mine/blog", method = RequestMethod.GET)
    public IronResponseEntity getMyBlogs(@Valid PageRequest pageRequest, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            List<BlogVO> blogVOList = blogService.pageMyBlogs(pageRequest);
            return new IronResponseEntity(ResponseStatus.SUCCESS, blogVOList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }


}
