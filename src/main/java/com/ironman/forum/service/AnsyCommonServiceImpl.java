package com.ironman.forum.service;

import com.ironman.forum.dao.*;
import com.ironman.forum.entity.*;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.ResponseStatus;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Log4j
public class AnsyCommonServiceImpl implements AnsyCommonService {
    @Autowired
    private TimeLineDAO timeLineDAO;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    private AboutMeDAO aboutMeDAO;

    @Autowired
    private LikeLogDAO likeLogDAO;

    @Override
    @Async
    public void ansyAddTimeLine(TimeLine timeLine) {
        timeLineDAO.save(timeLine);
    }

    @Override
    @Async
    public void ansyChangeEntityPropertyNumById(String table, long id, String property, boolean isIncrement) {
        commonDAO.changePropertyNumById(table, id, property, isIncrement);
    }

    /**
     * 根据log信息写入about_me表中
     *
     * @param baseLog
     * @throws GlobalException
     */
    @Async
    @Override
    public void ansySaveAboutMe(BaseLog baseLog) throws GlobalException {
        AboutMe aboutMe = new AboutMe();
        long targetId = baseLog.getTargetId();
        aboutMe.setLogId(baseLog.getId());
        aboutMe.setDeleted(false);
        aboutMe.setNew(true);
        aboutMe.setDeleted(false);
        aboutMe.setCreateTime(new Date());
        if (baseLog instanceof LikeLog) {
            aboutMe.setType(AboutMe.LogType.LIKE_LOG.getId());
        } else if (baseLog instanceof ViewLog) {
            aboutMe.setType(AboutMe.LogType.VIEW_LOG.getId());
        } else if (baseLog instanceof CommentLog) {
            aboutMe.setType(AboutMe.LogType.COMMENT_LOG.getId());
        } else {
            log.error("baseLog类型不合法");
            throw new GlobalException();
        }

        int type = baseLog.getType();

        if (type == ArticleType.COMMENT.getId()) {
            //todo
        } else if (type == ArticleType.MOMENT.getId()) {
            Moment moment = momentDAO.getBaseInfoById(targetId);
            if (moment == null) {
                throw new GlobalException(ResponseStatus.MOMENT_NOT_EXIST);
            }
            aboutMe.setUserId(moment.getUserId());
        } else if (type == ArticleType.BLOG.getId()) {
            Blog blog = blogDAO.getBaseInfoById(targetId);
            if (blog == null) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
            aboutMe.setUserId(blog.getUserId());
        } else if (type == ArticleType.QUESTION.getId()) {
            //todo
        } else {
            log.error("type类型不合法");
            throw new GlobalException();
        }
        aboutMeDAO.save(aboutMe);
    }

    @Override
    public void ansyDeleteAboutMe(BaseLog baseLog) throws GlobalException {
        int type;
        if (baseLog instanceof LikeLog) {
            type = AboutMe.LogType.LIKE_LOG.getId();
        } else if (baseLog instanceof ViewLog) {
            type = AboutMe.LogType.VIEW_LOG.getId();
        } else if (baseLog instanceof CommentLog) {
            type = AboutMe.LogType.COMMENT_LOG.getId();
        } else {
            log.error("baseLog类型不合法");
            throw new GlobalException();
        }
        aboutMeDAO.deleteByLogIdAndType(baseLog.getId(), type);
    }
}
