package com.ironman.forum.controller;

import com.ironman.forum.form.SearchForm;
import com.ironman.forum.service.SearchService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronResponseEntity;
import com.ironman.forum.util.IronUtil;
import com.ironman.forum.util.ResponseStatus;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 搜索相关控制器
 */
@RestController
@RequestMapping(value = "/data")
@Log4j
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 分页搜索接口
     *
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public IronResponseEntity searchEntity(@RequestBody @Valid SearchForm form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            List<Object> searchBlogVOList = searchService.searchEntity(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS, searchBlogVOList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }
}
