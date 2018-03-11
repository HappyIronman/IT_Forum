package com.ironman.forum.service;

import com.ironman.forum.dao.BlogDAO;
import com.ironman.forum.dao.MomentDAO;
import com.ironman.forum.dao.TimeLineDAO;
import com.ironman.forum.entity.ArticleType;
import com.ironman.forum.entity.Blog;
import com.ironman.forum.entity.Moment;
import com.ironman.forum.entity.TimeLine;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.BlogVO;
import com.ironman.forum.vo.MomentVO;
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
    private MomentService momentService;

    @Autowired
    private BlogService blogService;

    @Override
    public List<TimeLineVO> pageMyFriendCircle(PageRequest pageRequest) throws GlobalException {
        Long userId = 123L;
        List<TimeLine> timeLineList = timeLineDAO.getAllLimitByUserId(userId, pageRequest);
        List<TimeLineVO> timeLineVOList = new ArrayList<>();
        if (timeLineList == null || timeLineList.size() == 0) {
            return timeLineVOList;
        }
        for (TimeLine timeLine : timeLineList) {
            TimeLineVO timeLineVO = new TimeLineVO();
            int type = timeLine.getType();
            long eventId = timeLine.getEventId();
            timeLineVO.setType(type);
            if (type == ArticleType.MOMENT.getId()) {
                Moment moment = momentDAO.getById(eventId);
                MomentVO momentVO = momentService.assembleMomentVO(moment);
                timeLineVO.setEntity(momentVO);
            } else if (type == ArticleType.BLOG.getId()) {
                Blog blog = blogDAO.getById(eventId);
                BlogVO blogVO = blogService.assembleBlogVO(blog);
                timeLineVO.setEntity(blogVO);
            } else if (type == ArticleType.QUESTION.getId()) {
                //todo
            } else if (type == ArticleType.COMMENT.getId()) {
                //todo
            }
            timeLineVOList.add(timeLineVO);
        }
        return timeLineVOList;
    }

}
