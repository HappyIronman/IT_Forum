package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.BlogDAO;
import com.ironman.forum.dao.MomentDAO;
import com.ironman.forum.dao.QuestionDAO;
import com.ironman.forum.dao.TimeLineDAO;
import com.ironman.forum.entity.*;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.BlogAbsVO;
import com.ironman.forum.vo.MomentVO;
import com.ironman.forum.vo.QuestionVO;
import com.ironman.forum.vo.TimeLineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TimeLineServiceImpl implements TimeLineService {
    @Autowired
    private TimeLineDAO timeLineDAO;

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private MomentService momentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private QuestionService questionService;

    @Override
    public List<TimeLineVO> pageMyFriendCircle(PageRequest pageRequest) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        List<TimeLine> timeLineList = timeLineDAO.getAllLimitByUserId(userId, pageRequest);
        List<TimeLineVO> timeLineVOList = new ArrayList<>();
        if (timeLineList == null || timeLineList.size() == 0) {
            return timeLineVOList;
        }
        for (TimeLine timeLine : timeLineList) {
            TimeLineVO timeLineVO = new TimeLineVO();
            int type = timeLine.getType();
            long articleId = timeLine.getArticleId();
            timeLineVO.setType(type);
            if (type == ArticleTypeEnum.MOMENT.getId()) {
                Moment moment = momentDAO.getById(articleId);
                MomentVO momentVO = momentService.assembleMomentVO(moment);
                timeLineVO.setEntity(momentVO);
            } else if (type == ArticleTypeEnum.BLOG.getId()) {
                Blog blog = blogDAO.getById(articleId);
                BlogAbsVO blogAbsVO = blogService.assembleBlogAbsVO(blog);
                timeLineVO.setEntity(blogAbsVO);
            } else if (type == ArticleTypeEnum.QUESTION.getId()) {
                Question question = questionDAO.getById(articleId);
                QuestionVO questionVO = questionService.assembleQuestionAbsVO(question);
                timeLineVO.setEntity(questionVO);
            } else if (type == ArticleTypeEnum.COMMENT.getId()) {
                //todo
            }
            timeLineVOList.add(timeLineVO);
        }
        return timeLineVOList;
    }

}
