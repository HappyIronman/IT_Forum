package com.ironman.forum.controller;

import com.ironman.forum.form.CommentListForm;
import com.ironman.forum.form.CommentPublishForm;
import com.ironman.forum.service.CommentService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronResponseEntity;
import com.ironman.forum.util.IronUtil;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.CommentVO;
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
 * 评论相关控制器
 */
@RestController
@RequestMapping("/data")
@Log4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 发表评论(原创或转载都走此接口)
     *
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    IronResponseEntity publishComment(@RequestBody @Valid CommentPublishForm form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            CommentVO commentVO = commentService.publishComment(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS, commentVO);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }

    /**
     * 分页获取文章评论列表
     *
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    IronResponseEntity pageCommentList(@Valid CommentListForm form, BindingResult result) {
        if (result.hasErrors()) {
            return IronUtil.processResult(result);
        }
        try {
            List<CommentVO> commentVOList = commentService.pageCommentList(form);
            return new IronResponseEntity(ResponseStatus.SUCCESS, commentVOList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
            return new IronResponseEntity(e.getResponseStatus());
        }
    }
}
