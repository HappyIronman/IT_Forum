package com.ironman.forum.service;

import com.ironman.forum.dao.*;
import com.ironman.forum.entity.*;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronCache;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.CommentLog;
import com.ironman.forum.vo.FollowLog;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 异步操作类
 * 需要加入确保成功措施
 */
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
    private ViewLogDAO viewLogDAO;

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
            //只有博客相关的浏览记录会写入aboutMe表
            if (baseLog.getType() != EntityType.BLOG.getId()) {
                return;
            }
        } else if (baseLog instanceof CommentLog) {
            aboutMe.setType(AboutMe.LogType.COMMENT.getId());
        } else if (baseLog instanceof FollowLog) {
            aboutMe.setType(AboutMe.LogType.FOLLOW.getId());
        } else {
            log.error("baseLog类型不合法");
            throw new GlobalException();
        }

        int type = baseLog.getType();

        if (type == EntityType.USER.getId()) {
            aboutMe.setUserId(targetId);
        } else if (type == EntityType.COMMENT.getId()) {
            //todo
        } else if (type == EntityType.MOMENT.getId()) {
            Moment moment = momentDAO.getBaseInfoById(targetId);
            if (moment == null) {
                throw new GlobalException(ResponseStatus.MOMENT_NOT_EXIST);
            }
            aboutMe.setUserId(moment.getUserId());
        } else if (type == EntityType.BLOG.getId()) {
            Blog blog = blogDAO.getBaseInfoById(targetId);
            if (blog == null) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
            aboutMe.setUserId(blog.getUserId());
        } else if (type == EntityType.QUESTION.getId()) {
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
            type = AboutMe.LogType.COMMENT.getId();
        } else {
            log.error("baseLog类型不合法");
            throw new GlobalException();
        }
        aboutMeDAO.deleteByLogIdAndType(baseLog.getId(), type);
    }

    @Override
    public void ansySaveViewLog(ViewLog viewLog) throws GlobalException {
        if (IronCache.getViewLogCacheSize() > IronConstant.VIEW_LOG_MAX_CACHE_SIZE) {
            log.info("缓存已满,直接落库");
            this.increaseArticleViewLog(viewLog.getTargetId(), viewLog.getType(), 1);
            viewLogDAO.save(viewLog);
        } else {
            IronCache.addViewLog(viewLog);
        }
    }

    @Override
    public void ansySaveViewLogList(List<ViewLog> viewLogList) throws GlobalException {
        if (IronCache.getViewLogCacheSize() > IronConstant.VIEW_LOG_MAX_CACHE_SIZE) {
            log.info("缓存已满,直接落库");
            for (ViewLog viewLog : viewLogList) {
                this.increaseArticleViewLog(viewLog.getTargetId(), viewLog.getType(), 1);
                viewLogDAO.save(viewLog);
            }
        } else {
            for (ViewLog viewLog : viewLogList) {
                IronCache.addViewLog(viewLog);
            }
        }
    }

    @Override
    public void increaseArticleViewLog(long targetId, int type, int addNum) throws GlobalException {
        String property = IronConstant.ARTICLE_PROPERTY_VIEW_NUM;
        if (type == EntityType.COMMENT.getId()) {
            commonDAO.increasePropertyNumById(IronConstant.TABLE_COMMENT, targetId, property, addNum);
        } else if (type == EntityType.MOMENT.getId()) {
            commonDAO.increasePropertyNumById(IronConstant.TABLE_MOMENT, targetId, property, addNum);
        } else if (type == EntityType.BLOG.getId()) {
            commonDAO.increasePropertyNumById(IronConstant.TABLE_BLOG, targetId, property, addNum);
        } else if (type == EntityType.QUESTION.getId()) {
            commonDAO.increasePropertyNumById(IronConstant.TABLE_QUESTION, targetId, property, addNum);
        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }
    }
}
