package com.ironman.forum.service.aop;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.ViewLogDAO;
import com.ironman.forum.entity.ArticleTypeEnum;
import com.ironman.forum.entity.ViewLog;
import com.ironman.forum.service.CommonService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.BlogDetailVO;
import com.ironman.forum.vo.MomentVO;
import com.ironman.forum.vo.QuestionVO;
import com.ironman.forum.vo.TimeLineVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Log4j
public class SaveViewLogAspect {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ViewLogDAO viewLogDAO;

    public void afterReturningGetBlogDetail(String uniqueId, BlogDetailVO blogDetailVO) {
        if (blogDetailVO == null) {
            return;
        }
        ViewLog viewLog = new ViewLog();
        long userId = UserLoginUtil.getLoginUserId();
        viewLog.setUserId(userId);
        viewLog.setType(ArticleTypeEnum.BLOG.getId());
        viewLog.setTargetId(blogDetailVO.getId());
        viewLog.setDisabled(false);
        viewLog.setCreateTime(new Date());
        try {
            //异步增加访问量
            commonService.ansyIncreaseArticleViewLog(viewLog.getTargetId(), viewLog.getType(), 1);
            viewLogDAO.save(viewLog);
            //匿名用户直接返回，不写aboutMe表
            if (userId == IronConstant.ANONYMOUS_USER_ID) {
                return;
            }
            //自己看自己的博客不写入aboutMe
            if (!blogDetailVO.getUserId().equals(UserLoginUtil.getLoginUserUniqueId())) {
                commonService.ansySaveAboutMe(viewLog);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void afterReturningPageMoments(PageRequest pageRequest, List<MomentVO> momentVOList) {
        if (momentVOList == null || momentVOList.size() == 0) {
            return;
        }

        List<ViewLog> viewLogList = new ArrayList<>();
        for (MomentVO momentVO : momentVOList) {
            ViewLog viewLog = new ViewLog();
            viewLog.setUserId(UserLoginUtil.getLoginUserId());
            viewLog.setType(ArticleTypeEnum.MOMENT.getId());
            viewLog.setTargetId(momentVO.getId());
            viewLog.setDisabled(false);
            viewLog.setCreateTime(new Date());
            viewLogList.add(viewLog);
        }
        try {
            commonService.ansySaveViewLogList(viewLogList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void afterReturningPageUserMoments(String userUniqueId, PageRequest pageRequest, List<MomentVO> momentVOList) {
        this.afterReturningPageMoments(pageRequest, momentVOList);
    }

    public void afterReturningPageQuestions(PageRequest pageRequest, List<QuestionVO> questionVOList) {
        if (questionVOList == null || questionVOList.size() == 0) {
            return;
        }

        List<ViewLog> viewLogList = new ArrayList<>();
        for (QuestionVO questionVO : questionVOList) {
            ViewLog viewLog = new ViewLog();
            viewLog.setUserId(UserLoginUtil.getLoginUserId());
            viewLog.setType(ArticleTypeEnum.QUESTION.getId());
            viewLog.setTargetId(questionVO.getId());
            viewLog.setDisabled(false);
            viewLog.setCreateTime(new Date());
            viewLogList.add(viewLog);
        }
        try {
            commonService.ansySaveViewLogList(viewLogList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void afterReturningPageUserQuestions(String userUniqueId, PageRequest pageRequest, List<QuestionVO> questionVOList) {
        this.afterReturningPageQuestions(pageRequest, questionVOList);
    }

    public void afterReturningGetQuestionDetail(String uniqueId, QuestionVO questionVO) {
        if (questionVO == null) {
            return;
        }
        ViewLog viewLog = new ViewLog();
        long userId = UserLoginUtil.getLoginUserId();
        viewLog.setUserId(userId);
        viewLog.setType(ArticleTypeEnum.QUESTION.getId());
        viewLog.setTargetId(questionVO.getId());
        viewLog.setDisabled(false);
        viewLog.setCreateTime(new Date());
        try {
            //异步增加访问量
            commonService.ansyIncreaseArticleViewLog(viewLog.getTargetId(), viewLog.getType(), 1);
            //落库访问记录
            commonService.ansySaveViewLog(viewLog);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    public void afterReturningPageMyFriendCircle(PageRequest pageRequest, List<TimeLineVO> timeLineVOList) {
        if (timeLineVOList == null || timeLineVOList.size() == 0) {
            return;
        }

        List<ViewLog> viewLogList = new ArrayList<>();
        for (TimeLineVO timeLineVO : timeLineVOList) {
            int type = timeLineVO.getType();
            ViewLog viewLog = new ViewLog();
            viewLog.setUserId(UserLoginUtil.getLoginUserId());
            viewLog.setType(type);
            viewLog.setDisabled(false);
            viewLog.setCreateTime(new Date());
            if (type == ArticleTypeEnum.MOMENT.getId()) {
                MomentVO momentVO = (MomentVO) timeLineVO.getEntity();
                viewLog.setTargetId(momentVO.getId());
            } else if (type == ArticleTypeEnum.QUESTION.getId()) {
                QuestionVO questionVO = (QuestionVO) timeLineVO.getEntity();
                viewLog.setTargetId(questionVO.getId());
            } else if (type == ArticleTypeEnum.COMMENT.getId()) {
                //todo
            }
            viewLogList.add(viewLog);
        }
        try {
            commonService.ansySaveViewLogList(viewLogList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
        }
    }
}
