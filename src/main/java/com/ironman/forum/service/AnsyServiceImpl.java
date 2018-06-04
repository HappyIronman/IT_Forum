package com.ironman.forum.service;

import com.ironman.forum.dao.*;
import com.ironman.forum.entity.*;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.CommentLog;
import com.ironman.forum.vo.FollowLog;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j
public class AnsyServiceImpl implements AnsyService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SearchLogDAO searchLogDAO;

    @Autowired
    private AboutMeDAO aboutMeDAO;

    @Autowired
    private ViewLogDAO viewLogDAO;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private TimeLineDAO timeLineDAO;

    @Autowired
    private FollowDAO followDAO;

    @Override
    @Async
    public void ansyChangeEntityPropertyNumById(String table, long id, String property, boolean isIncrement) {
        commonDAO.changePropertyNumById(table, id, property, isIncrement);
    }

    /**
     * 异步写入aboutme表
     */
    @Override
    @Async
    public void ansySaveAboutMe(AboutMe aboutMe) throws GlobalException {
        aboutMeDAO.save(aboutMe);
    }

    @Override
    @Async
    public void ansyIncreasePropertyNumById(String table, long id, String property, int addNum) {
        commonDAO.increasePropertyNumById(table, id, property, addNum);
    }

    @Override
    @Async
    public void ansyUpdateSearchLog(SearchLog searchLog) {
        searchLogDAO.update(searchLog);
    }


    @Override
    @Async
    public void ansyIncreaseArticleViewLog(long targetId, int type, int addNum) throws GlobalException {
        String property = IronConstant.ARTICLE_PROPERTY_VIEW_NUM;
        if (type == ArticleTypeEnum.COMMENT.getId()) {
            commonDAO.increasePropertyNumById(IronConstant.TABLE_COMMENT, targetId, property, addNum);
        } else if (type == ArticleTypeEnum.MOMENT.getId()) {
            commonDAO.increasePropertyNumById(IronConstant.TABLE_MOMENT, targetId, property, addNum);
        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            commonDAO.increasePropertyNumById(IronConstant.TABLE_BLOG, targetId, property, addNum);
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {
            commonDAO.increasePropertyNumById(IronConstant.TABLE_QUESTION, targetId, property, addNum);
        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }
    }

    @Override
    @Async
    public void ansySaveAboutMe(BaseLog baseLog) throws GlobalException {
        AboutMe aboutMe = new AboutMe();
        aboutMe.setLogId(baseLog.getId());
        aboutMe.setDeleted(false);
        aboutMe.setNew(true);
        aboutMe.setDeleted(false);
        aboutMe.setCreateTime(new Date());
        if (baseLog instanceof LikeLog) {
            aboutMe.setType(AboutMe.LogType.LIKE_LOG.getId());
        } else if (baseLog instanceof ViewLog) {
            aboutMe.setType(AboutMe.LogType.VIEW_LOG.getId());
            //只有浏览博客需要写入aboutme表
            if (baseLog.getType() != ArticleTypeEnum.BLOG.getId()) {
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

        int articleType = baseLog.getType();
        long userId;
        long targetId = baseLog.getTargetId();
        if (articleType == ArticleTypeEnum.USER.getId()) {
            userId = targetId;
            aboutMe.setUserId(targetId);
        } else {
            Article article = commonService.getArticleBaseInfoByIdAndType(targetId, articleType);
            userId = article.getUserId();
        }
        aboutMe.setUserId(userId);


        aboutMeDAO.save(aboutMe);
        //增加user表中new_about_me_num
        commonDAO.changePropertyNumById(IronConstant.TABLE_USER, userId,
                IronConstant.USER_PROPERTY_NEW_ABOUT_ME_NUM, true);
    }


    @Override
    @Async
    public void ansyDeleteAboutMe(BaseLog baseLog) throws GlobalException {
        int type;
        if (baseLog instanceof LikeLog) {
            type = AboutMe.LogType.LIKE_LOG.getId();
        } else if (baseLog instanceof ViewLog) {
            type = AboutMe.LogType.VIEW_LOG.getId();
        } else if (baseLog instanceof CommentLog) {
            type = AboutMe.LogType.COMMENT.getId();
        } else {
            log.error("baseLog���Ͳ��Ϸ�");
            throw new GlobalException();
        }

        long logId = baseLog.getId();

        AboutMe aboutMe = aboutMeDAO.getByLogIdAndType(logId, type);

        aboutMeDAO.deleteByLogIdAndType(logId, type);

        int articleType = baseLog.getType();
        long userId;
        long targetId = baseLog.getTargetId();
        if (articleType == ArticleTypeEnum.USER.getId()) {
            userId = targetId;
        } else {
            Article article = commonService.getArticleBaseInfoByIdAndType(targetId, articleType);
            userId = article.getUserId();
        }

        //如果是新消息，则减少user表中new_about_me_num
        if (aboutMe != null && aboutMe.isNew()) {
            commonDAO.changePropertyNumById(IronConstant.TABLE_USER, userId,
                    IronConstant.USER_PROPERTY_NEW_ABOUT_ME_NUM, false);
        }
    }

    @Override
    @Async
    public void ansySaveViewLog(ViewLog viewLog) throws GlobalException {
        if (IronCache.getViewLogCacheSize() > IronConstant.VIEW_LOG_MAX_CACHE_SIZE) {
            log.info("cache数量超限,直接落库");
            this.ansyIncreaseArticleViewLog(viewLog.getTargetId(), viewLog.getType(), 1);
            viewLogDAO.save(viewLog);
        } else {
            IronCache.addViewLog(viewLog);
        }
    }

    @Override
    @Async
    public void ansySaveViewLogList(List<ViewLog> viewLogList) throws GlobalException {
        if (CollectionUtils.isEmpty(viewLogList)) {
            return;
        }
        if (IronCache.getViewLogCacheSize() + viewLogList.size() > IronConstant.VIEW_LOG_MAX_CACHE_SIZE) {
            log.info("cache数量超限,直接落库");
            for (ViewLog viewLog : viewLogList) {
                this.ansyIncreaseArticleViewLog(viewLog.getTargetId(), viewLog.getType(), 1);
                viewLogDAO.save(viewLog);
            }
        } else {
            for (ViewLog viewLog : viewLogList) {
                IronCache.addViewLog(viewLog);
            }
        }
    }

    @Override
    @Async
    public void ansyIncreaseCommentNum(Comment comment) {
        int type = comment.getType();
        long targetId = comment.getReplyId();

        this.ansyChangeArticlePropertyNum(type, targetId, IronConstant.ARTICLE_PROPERTY_COMMENT_NUM, true);
        if (type != ArticleTypeEnum.COMMENT.getId()) {
            return;
        }
        Comment parent = commentDAO.getBaseInfoById(targetId);
        this.ansyIncreaseCommentNum(parent);
    }

    @Override
    public void ansyChangeArticlePropertyNum(int type, long targetId, String property, boolean isIncrement) {
        if (type == ArticleTypeEnum.COMMENT.getId()) {
            commonDAO.changePropertyNumById(IronConstant.TABLE_COMMENT, targetId, property, isIncrement);
        } else if (type == ArticleTypeEnum.MOMENT.getId()) {
            commonDAO.changePropertyNumById(IronConstant.TABLE_MOMENT, targetId, property, isIncrement);
        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            commonDAO.changePropertyNumById(IronConstant.TABLE_BLOG, targetId, property, isIncrement);
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {
            commonDAO.changePropertyNumById(IronConstant.TABLE_QUESTION, targetId, property, isIncrement);
        }
    }

    @Override
    public void ansyChangeUserPropertyNum(long userId, String property, boolean isIncrement) {
        commonDAO.changePropertyNumById(IronConstant.TABLE_USER, userId, property, isIncrement);
    }


    @Override
    public void ansyAddTimeLine(Long userId, long articleId, int type) {

        Date createTime = new Date();
        //插入我自己的时间轴
        TimeLine timeLine = new TimeLine();
        timeLine.setUserId(userId);
        timeLine.setArticleId(articleId);
        timeLine.setType(type);
        timeLine.setNew(true);
        timeLine.setSelf(true);
        timeLine.setCreateTime(createTime);
        timeLineDAO.save(timeLine);

        //分批插入粉丝的时间轴
        int batchSize = 200;
        PageRequest pageRequest = new PageRequest(0, batchSize);
        List<Follow> followList;
        do {
            followList = followDAO.getAllLimitByUserId(userId, pageRequest);
            if (CollectionUtils.isEmpty(followList)) {
                break;
            }
            List<TimeLine> timeLineList = new ArrayList<>();
            for (Follow follow : followList) {
                timeLine = new TimeLine();
                timeLine.setUserId(follow.getFollowerId());
                timeLine.setArticleId(articleId);
                timeLine.setType(type);
                timeLine.setNew(true);
                timeLine.setSelf(false);
                timeLine.setCreateTime(createTime);
                timeLineList.add(timeLine);
            }
            //批量插入
            timeLineDAO.batchSave1(timeLineList);
            pageRequest.nextPage();
        } while (followList.size() == batchSize);
    }

    @Override
    @Async
    public void ansyUpdateAboutMeStatus(List<AboutMe> aboutMeList) {
        if (CollectionUtils.isEmpty(aboutMeList)) {
            return;
        }
        for (AboutMe aboutMe : aboutMeList) {
            if (aboutMe.isNew()) {
                aboutMeDAO.updateStatusById(aboutMe.getId());
            }
        }
    }
}
