package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.*;
import com.ironman.forum.entity.*;
import com.ironman.forum.form.LikeArticleFormBean;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.CommentLog;
import com.ironman.forum.vo.FollowLog;
import com.ironman.forum.vo.ImageVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Log4j
public class CommonServiceImpl implements CommonService {


    @Autowired
    private LikeLogDAO likeLogDAO;

    @Autowired
    private AnsyCommonService ansyCommonService;

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private ViewLogDAO viewLogDAO;

    @Autowired
    private FollowDAO followDAO;

    @Autowired
    private TimeLineDAO timeLineDAO;

    @Value("#{prop.user_pic_path}")
    private String userPicPath;

    @Value("#{prop.host}")
    private String host;


    @Override
    public void likeArticle(LikeArticleFormBean formBean) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        int type = formBean.getType();
        Long targetId = this.getArticleBaseInfoByUniqueIdAndType(formBean.getTargetId(), type).getId();
        boolean isLike = formBean.isLike();

        LikeLog existLikeLog = likeLogDAO.getByUserIdAndTargetIdAndType(userId, targetId, type);
        if (existLikeLog != null) {
            throw new GlobalException(ResponseStatus.DUPLICATE_LIKE_LOG);
        }


        LikeLog likeLog = new LikeLog();
        likeLog.setUserId(userId);
        likeLog.setTargetId(targetId);
        likeLog.setType(type);
        likeLog.setLike(isLike);
        likeLog.setDisabled(false);
        likeLog.setCreateTime(new Date());

        likeLogDAO.save(likeLog);

        //增加赞数量
        String property = isLike ? IronConstant.ARTICLE_PROPERTY_LIKE_NUM : IronConstant.ARTICLE_PROPERTY_DISLIKE_NUM;
        this.ansyChangeArticlePropertyNum(type, targetId, property, true);

        //异步写入aboutme
        this.ansySaveAboutMe(likeLog);
    }

    @Override
    @Transactional
    public void cancelLikeArticle(LikeArticleFormBean formBean) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        int type = formBean.getType();
        Long targetId = this.getArticleBaseInfoByUniqueIdAndType(formBean.getTargetId(), type).getId();
        boolean isLike = formBean.isLike();
        LikeLog likeLog = likeLogDAO.getByUserIdAndTargetIdAndType(userId, targetId, type);
        if (null == likeLog) {
            //赞或踩记录不存在
            throw new GlobalException(ResponseStatus.LOG_NOT_EXIST);
        }
        likeLogDAO.updateDisabledByUserIdAndTargetIdAndTypeAndIsLike(userId, targetId, type, isLike);
        //异步减少赞或踩数量
        String property = isLike ? IronConstant.ARTICLE_PROPERTY_LIKE_NUM : IronConstant.ARTICLE_PROPERTY_DISLIKE_NUM;
        this.ansyChangeArticlePropertyNum(type, targetId, property, false);
        //异步删除aboutme
        this.ansyDeleteAboutMe(likeLog);
    }


    @Override
    public Article getArticleBaseInfoByUniqueIdAndType(String uniqueId, int type) throws GlobalException {
        Article article;
        if (type == ArticleTypeEnum.COMMENT.getId()) {
            article = commentDAO.getBaseInfoByUniqueId(uniqueId);
            if (article == null) {
                throw new GlobalException(ResponseStatus.COMMENT_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.MOMENT.getId()) {
            article = momentDAO.getBaseInfoByUniqueId(uniqueId);
            if (article == null) {
                throw new GlobalException(ResponseStatus.MOMENT_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            article = blogDAO.getBaseInfoByUniqueId(uniqueId);
            if (article == null) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {
            article = questionDAO.getBaseInfoByUniqueId(uniqueId);
            if (article == null) {
                throw new GlobalException(ResponseStatus.QUESTION_NOT_EXIST);
            }
        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }
        return article;
    }

    @Override
    public Article getArticleBaseInfoByIdAndType(long id, int type) throws GlobalException {
        Article article;
        if (type == ArticleTypeEnum.COMMENT.getId()) {
            article = commentDAO.getBaseInfoById(id);
            if (article == null) {
                throw new GlobalException(ResponseStatus.COMMENT_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.MOMENT.getId()) {
            article = momentDAO.getBaseInfoById(id);
            if (article == null) {
                throw new GlobalException(ResponseStatus.MOMENT_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            article = blogDAO.getBaseInfoById(id);
            if (article == null) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {
            article = questionDAO.getBaseInfoById(id);
            if (article == null) {
                throw new GlobalException(ResponseStatus.QUESTION_NOT_EXIST);
            }
        } else {
            log.error("type不存在");
            throw new GlobalException();
        }
        return article;
    }

    @Override
    public Article getArticleDetailInfoByIdAndType(long id, int type) throws GlobalException {
        Article article;
        if (type == ArticleTypeEnum.COMMENT.getId()) {
            article = commentDAO.getById(id);
            if (article == null) {
                throw new GlobalException(ResponseStatus.COMMENT_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.MOMENT.getId()) {
            article = momentDAO.getById(id);
            if (article == null) {
                throw new GlobalException(ResponseStatus.MOMENT_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            article = blogDAO.getById(id);
            if (article == null) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {
            article = questionDAO.getById(id);
            if (article == null) {
                throw new GlobalException(ResponseStatus.QUESTION_NOT_EXIST);
            }
        } else {
            log.error("type不存在");
            throw new GlobalException();
        }
        return article;
    }

    @Override
    public int judgeLikeCondition(Article article) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        int type;
        if (article instanceof Comment) {
            type = ArticleTypeEnum.COMMENT.getId();
        } else if (article instanceof Moment) {
            type = ArticleTypeEnum.MOMENT.getId();
        } else if (article instanceof Blog) {
            type = ArticleTypeEnum.BLOG.getId();
        } else if (article instanceof Question) {
            type = ArticleTypeEnum.QUESTION.getId();
        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }
        LikeLog likeLog = likeLogDAO.getByUserIdAndTargetIdAndType(userId, article.getId(), type);
        if (likeLog != null) {
            return (likeLog.isLike() ? IronConstant.LIKE_CONDITION_LIKED : IronConstant.LIKE_CONDITION_DISLIKED);
        } else {
            return IronConstant.LIKE_CONDITION_DEFAULT;
        }
    }

    @Override
    public void ansyIncreaseArticleViewLog(long targetId, int type, int addNum) throws GlobalException {
        String property = IronConstant.ARTICLE_PROPERTY_VIEW_NUM;
        if (type == ArticleTypeEnum.COMMENT.getId()) {
            ansyCommonService.ansyIncreasePropertyNumById(IronConstant.TABLE_COMMENT, targetId, property, addNum);
        } else if (type == ArticleTypeEnum.MOMENT.getId()) {
            ansyCommonService.ansyIncreasePropertyNumById(IronConstant.TABLE_MOMENT, targetId, property, addNum);
        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            ansyCommonService.ansyIncreasePropertyNumById(IronConstant.TABLE_BLOG, targetId, property, addNum);
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {
            ansyCommonService.ansyIncreasePropertyNumById(IronConstant.TABLE_QUESTION, targetId, property, addNum);
        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }
    }

    @Override
    @Transactional
    public List<ImageVO> saveImages(MultipartFile[] images) throws GlobalException {
        if (images == null || images.length == 0) {
            log.error("图片文件为空");
            throw new GlobalException(ResponseStatus.PARAM_ERROR);
        }
        List<ImageVO> imageVOList = new ArrayList<>();
        for (MultipartFile multipartFile : images) {
            String originName = multipartFile.getOriginalFilename();
            log.info("originName: " + originName);
            String suffix = originName.substring(originName.lastIndexOf("."));
            String newName = IronUtil.generateUniqueId() + suffix;
            log.info("newName:" + newName);
            String path;
            if (StringUtils.isEmpty(this.userPicPath)) {
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR, "存储路径为空");
            }
            if (this.userPicPath.endsWith("/")) {
                path = this.userPicPath + newName;
            } else {
                path = this.userPicPath + "/" + newName;
            }
            try {
                multipartFile.transferTo(new File(path));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR, "ͼƬ�洢�쳣");
            }
            String picUrl = this.concatImageUrl(newName);
            ImageVO imageVO = new ImageVO();
            imageVO.setName(newName);
            imageVO.setUrl(picUrl);
            imageVOList.add(imageVO);
        }
        return imageVOList;
    }

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

        int type = baseLog.getType();
        if (type == ArticleTypeEnum.USER.getId()) {
            aboutMe.setUserId(targetId);
        } else {
            Article article = this.getArticleBaseInfoByIdAndType(targetId, type);
            aboutMe.setUserId(article.getUserId());
        }


        ansyCommonService.ansySaveAboutMe(aboutMe);
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
            log.error("baseLog���Ͳ��Ϸ�");
            throw new GlobalException();
        }
        ansyCommonService.ansyDeleteAboutMe(baseLog.getId(), type);
    }

    @Override
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
    public void ansySaveViewLogList(List<ViewLog> viewLogList) throws GlobalException {
        if (IronCache.getViewLogCacheSize() > IronConstant.VIEW_LOG_MAX_CACHE_SIZE) {
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
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_COMMENT, targetId, property, isIncrement);
        } else if (type == ArticleTypeEnum.MOMENT.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_MOMENT, targetId, property, isIncrement);
        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_BLOG, targetId, property, isIncrement);
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_QUESTION, targetId, property, isIncrement);
        }
    }

    @Override
    public void ansyChangeUserPropertyNum(long userId, String property, boolean isIncrement) {
        ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_USER, userId, property, isIncrement);
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
        List<Follow> followList = new ArrayList<>(batchSize);
        while (followList.size() == batchSize) {
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
                timeLine.setSelf(true);
                timeLine.setCreateTime(createTime);
                timeLineList.add(timeLine);
            }
            //批量插入
            timeLineDAO.batchSave(timeLineList);

            pageRequest.nextPage();
        }
    }

    @Override
    public String concatImageUrl(String imgName){
        String picUrl;

        if (host.endsWith("/")) {
            picUrl = host + "img/" + imgName;
        } else {
            picUrl = host + "/img/" + imgName;
        }
        return picUrl;
    }

    public String getUserPicPath() {
        return userPicPath;
    }

    public String getHost() {
        return host;
    }

    public void setUserPicPath(String userPicPath) {
        this.userPicPath = userPicPath;
    }

    public void setHost(String host) {
        this.host = host;
    }


}
