package com.ironman.forum.controller;

import com.ironman.forum.form.MomentPublishForm;
import com.ironman.forum.service.MomentService;
import com.ironman.forum.util.*;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.MomentVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 动态相关控制器
 */
@RestController
@RequestMapping("/data")
@Log4j
public class MomentController {

    @Autowired
    private MomentService momentService;

    /**
     * 发表动态
     *
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/moment", method = RequestMethod.POST)
    public IronResponseEntity publishMoment(@RequestBody @Valid MomentPublishForm form, BindingResult result) throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        momentService.publishMoment(form);
        return new IronResponseEntity(ResponseStatus.SUCCESS);
    }

    /**
     * 分页获取自己的动态列表
     *
     * @param pageRequest
     * @param result
     * @return
     */
    @RequestMapping(value = "/mymoments", method = RequestMethod.GET)
    public IronResponseEntity pageMyMoments(@Valid PageRequest pageRequest, BindingResult result) throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        List<MomentVO> momentVOList = momentService.pageMyMoments(pageRequest);
        return new IronResponseEntity(ResponseStatus.SUCCESS, momentVOList);
    }

    /**
     * 分页获取用户动态列表
     *
     * @param userUniqueId
     * @param pageRequest
     * @param result
     * @return
     */
    @RequestMapping(value = "/{userId}/moments", method = RequestMethod.GET)
    public IronResponseEntity pageUserMoments(@PathVariable("userId") String userUniqueId,
                                             @Valid PageRequest pageRequest, BindingResult result) throws GlobalException {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        List<MomentVO> momentVOList = momentService.pageUserMoments(userUniqueId, pageRequest);
        return new IronResponseEntity(ResponseStatus.SUCCESS, momentVOList);
    }

}
