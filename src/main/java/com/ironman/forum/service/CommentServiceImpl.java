package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.CommentDAO;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.Article;
import com.ironman.forum.entity.Comment;
import com.ironman.forum.entity.User;
import com.ironman.forum.form.CommentListForm;
import com.ironman.forum.form.CommentPublishForm;
import com.ironman.forum.util.BeanUtils;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronUtil;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.CommentLog;
import com.ironman.forum.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private UserDAO userDAO;


    @Override
    public CommentVO publishComment(CommentPublishForm form) throws GlobalException {
        //校验逻辑
        String replyUniqueId = form.getReplyId();
        int type = form.getType();
        String content = form.getContent();

        Article article = commonService.getArticleBaseInfoByUniqueIdAndType(replyUniqueId, type);

        Comment comment = new Comment();
        comment.setType(type);
        comment.setReplyId(article.getId());

        String uniqueId = IronUtil.generateUniqueId();
        comment.setUniqueId(uniqueId);

        long userId = UserLoginUtil.getLoginUserId();
        comment.setUserId(userId);

        comment.setContent(content);
        comment.setDeleted(false);
        comment.setCreateTime(new Date());

        commentDAO.save(comment);

        //异步递归增加评论数量
        commonService.ansyIncreaseCommentNum(comment);

        //异步写入aboutMe
        CommentLog commentLog=new CommentLog();
        commentLog.setId(comment.getId());
        commentLog.setUserId(userId);
        commentLog.setTargetId(article.getId());
        commentLog.setType(type);
        commentLog.setCreateTime(new Date());
        commentLog.setDisabled(false);
        commonService.ansySaveAboutMe(commentLog);

        return this.assembleCommentVO(comment);
    }


    @Override
    public List<CommentVO> pageCommentList(CommentListForm form) throws GlobalException {
        int type = form.getType();
        int page = form.getPage();
        int size = form.getSize();
        Article article = commonService.getArticleBaseInfoByUniqueIdAndType(form.getReplyId(), type);
        long replyId = article.getId();

        List<Comment> commentList = commentDAO.pageByReplyIdAndType(replyId, type, new PageRequest(page, size, PageRequest.Sort.ASC));

        List<CommentVO> commentVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(commentList)) {
            return commentVOList;
        }
        for (Comment comment : commentList) {
            CommentVO commentVO = this.assembleCommentVO(comment);
            commentVOList.add(commentVO);
        }
        return commentVOList;
    }


    private CommentVO assembleCommentVO(Comment comment) {
        CommentVO commentVO = BeanUtils.copy(comment, CommentVO.class);
        long userId = comment.getUserId();
        User user = userDAO.getArticleBaseInfoById(userId);
        commentVO.setUserId(user.getUniqueId());
        commentVO.setUsername(user.getUsername());
        commentVO.setProfile(user.getProfile());
        return commentVO;
    }


}
