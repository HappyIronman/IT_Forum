package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.*;
import com.ironman.forum.entity.*;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.IronUtil;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.ImageVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
@Log4j
public class CommonServiceImpl implements CommonService {


    @Autowired
    private LikeLogDAO likeLogDAO;

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private QuestionDAO questionDAO;

    @Value("#{prop.user_pic_path}")
    private String userPicPath;

    @Value("#{prop.host}")
    private String host;


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
        //若未登录,直接返回默认状态
        if (userId == IronConstant.ANONYMOUS_USER_ID) {
            return IronConstant.LIKE_CONDITION_DEFAULT;
        }
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
    public String concatImageUrl(String imgName) {
        if (StringUtils.isEmpty(imgName)) {
            return null;
        }
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
