package com.ironman.forum.service;

import com.ironman.forum.form.CommentListForm;
import com.ironman.forum.form.CommentPublishForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.vo.CommentVO;

import java.util.List;

public interface CommentService {
    CommentVO publishComment(CommentPublishForm form) throws GlobalException;

    List<CommentVO> pageCommentList(CommentListForm form) throws GlobalException;
}
