package com.ironman.forum.controller;

import com.ironman.forum.form.MomentPublishForm;
import com.ironman.forum.service.MomentService;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.MomentVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/data")
@Log4j
public class MomentController {

    @Autowired
    private MomentService momentService;

    @RequestMapping(value = "/moment", method = RequestMethod.POST)
    public IronResponseEntity publishMoment(@RequestBody @Valid MomentPublishForm form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            momentService.publishMoment(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    @RequestMapping(value = "/mine/moment", method = RequestMethod.GET)
    public IronResponseEntity getMyMoments(@Valid PageRequest pageRequest, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            List<MomentVO> momentVOList = momentService.pageMyMoments(pageRequest);
            return new IronResponseEntity(ResponseStatus.SUCCESS, momentVOList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

}
