package com.ironman.forum.controller;

import com.ironman.forum.form.QuestionPublishForm;
import com.ironman.forum.service.QuestionService;
import com.ironman.forum.util.*;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.QuestionVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 提问相关
 */
@RestController
@RequestMapping(value = "/data")
@Log4j
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 发表提问
     */
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public IronResponseEntity publishQuestion(@RequestBody @Valid QuestionPublishForm form,
                                              BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        questionService.publishQuestion(form);
        return new IronResponseEntity(ResponseStatus.SUCCESS);
    }

    /**
     * 问题详细信息
     */
    @RequestMapping(value = "/question/{uniqueId}", method = RequestMethod.GET)
    public IronResponseEntity questionDetail(@PathVariable("uniqueId") String uniqueId) throws GlobalException {
        QuestionVO questionVO = questionService.getQuestionDetail(uniqueId);
        return new IronResponseEntity(ResponseStatus.SUCCESS, questionVO);
    }

    /**
     * 我的问题列表
     */
    @RequestMapping(value = "/myquestions", method = RequestMethod.GET)
    public IronResponseEntity pageMyQuestions(@Valid PageRequest pageRequest, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        List<QuestionVO> questionVOList = questionService.pageMyQuestions(pageRequest);
        return new IronResponseEntity(ResponseStatus.SUCCESS, questionVOList);
    }

    /**
     * 用户问题列表
     *
     * @param userUniqueId
     * @param pageRequest
     * @param result
     * @return
     */
    @RequestMapping(value = "/{userId}/questions", method = RequestMethod.GET)
    IronResponseEntity pageUserQuestions(@PathVariable("userId") String userUniqueId,
                                         @Valid PageRequest pageRequest, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        List<QuestionVO> questionVOList = questionService.pageUserQuestions(userUniqueId, pageRequest);
        return new IronResponseEntity(ResponseStatus.SUCCESS, questionVOList);
    }
}
