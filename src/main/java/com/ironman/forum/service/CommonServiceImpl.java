package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.*;
import com.ironman.forum.entity.Blog;
import com.ironman.forum.entity.EntityType;
import com.ironman.forum.entity.LikeLog;
import com.ironman.forum.entity.Moment;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.IronUtil;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.ImageVO;
import com.ironman.forum.vo.LikeArticleFormBean;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private ViewLogDAO viewLogDAO;

    @Autowired
    private CommonDAO commonDAO;

    @Value("#{prop.user_pic_path}")
    private String userPicPath;

    @Value("#{prop.host}")
    private String host;


    @Override
    public void likeArticle(LikeArticleFormBean formBean) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        int type = formBean.getType();
        Long targetId = this.getArticleIdByUniqueIdAndType(formBean.getTargetId(), type);
        boolean isLike = formBean.isLike();
        //加共享锁处理并发问题
        LikeLog existLikeLog = likeLogDAO.getByUserIdAndTargetIdAndType(userId, targetId, type);
        if (existLikeLog != null) {
            //不能重复赞或者踩
            throw new GlobalException(ResponseStatus.DUPLICATE_LIKE_LOG);
        }

        //增加赞或踩记录
        LikeLog likeLog = new LikeLog();
        likeLog.setUserId(userId);
        likeLog.setTargetId(targetId);
        likeLog.setType(type);
        likeLog.setLike(isLike);
        likeLog.setDisabled(false);
        likeLog.setCreateTime(new Date());

        likeLogDAO.save(likeLog);

        //异步增加对应文章赞或踩数量
        this.changeArticleLikeNum(likeLog, true);

        //异步写入aboutMe表
        ansyCommonService.ansySaveAboutMe(likeLog);
    }

    @Override
    @Transactional
    public void cancelLikeArticle(LikeArticleFormBean formBean) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        int type = formBean.getType();
        Long targetId = this.getArticleIdByUniqueIdAndType(formBean.getTargetId(), type);
        boolean isLike = formBean.isLike();
        LikeLog likeLog = likeLogDAO.getByUserIdAndTargetIdAndType(userId, targetId, type);
        if (null == likeLog) {
            //证明表中无匹配记录，报错
            throw new GlobalException(ResponseStatus.LOG_NOT_EXIST);
        }
        likeLogDAO.updateDisabledByUserIdAndTargetIdAndTypeAndIsLike(userId, targetId, type, isLike);
        //异步减少对应文章赞或踩数量
        this.changeArticleLikeNum(likeLog, false);
        //异步删除aboutMe表记录
        ansyCommonService.ansyDeleteAboutMe(likeLog);
    }


    @Override
    public Long getArticleIdByUniqueIdAndType(String uniqueId, int type) throws GlobalException {
        Long id;
        if (type == EntityType.COMMENT.getId()) {
            //todo
            id = null;
        } else if (type == EntityType.MOMENT.getId()) {
            Moment moment = momentDAO.getBaseInfoByUniqueId(uniqueId);
            if (moment == null) {
                throw new GlobalException(ResponseStatus.MOMENT_NOT_EXIST);
            }
            id = moment.getId();
        } else if (type == EntityType.BLOG.getId()) {
            Blog blog = blogDAO.getBaseInfoByUniqueId(uniqueId);
            if (blog == null) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
            id = blog.getId();
        } else if (type == EntityType.QUESTION.getId()) {
            //todo
            id = null;
        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }
        return id;
    }


    @Override
    @Transactional
    public List<ImageVO> saveImages(MultipartFile[] images) throws GlobalException {
        if (images == null || images.length == 0) {
            log.error("文件为空");
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
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR, "图片存储路径为空");
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
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR, "图片存储异常");
            }
            String picUrl = IronUtil.concatImageUrl(this.host, newName);
            ImageVO imageVO = new ImageVO();
            imageVO.setName(newName);
            imageVO.setUrl(picUrl);
            imageVOList.add(imageVO);
        }
        return imageVOList;
    }

    /**
     * 异步改变对应文章赞或踩数量
     *
     * @param likeLog
     */
    private void changeArticleLikeNum(LikeLog likeLog, boolean isIncrement) {
        int type = likeLog.getType();
        long targetId = likeLog.getTargetId();
        String property = likeLog.isLike() ? IronConstant.ARTICLE_PROPERTY_LIKE_NUM : IronConstant.ARTICLE_PROPERTY_DISLIKE_NUM;
        if (type == EntityType.COMMENT.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_COMMENT, targetId, property, isIncrement);
        } else if (type == EntityType.MOMENT.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_MOMENT, targetId, property, isIncrement);
        } else if (type == EntityType.BLOG.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_BLOG, targetId, property, isIncrement);
        } else if (type == EntityType.QUESTION.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_QUESTION, targetId, property, isIncrement);
        }
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
