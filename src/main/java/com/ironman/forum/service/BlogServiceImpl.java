package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.BlogDAO;
import com.ironman.forum.dao.LikeLogDAO;
import com.ironman.forum.dao.ShareDAO;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.*;
import com.ironman.forum.form.BlogPublishForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.BlogAbsVO;
import com.ironman.forum.vo.BlogDetailVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ShareDAO shareDAO;

    @Autowired
    private LikeLogDAO likeLogDAO;

    @Autowired
    private AnsyCommonService ansyCommonService;


    @Override
    @Transactional
    public String publishBlog(BlogPublishForm form) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        String title = form.getTitle();
        String content = form.getContent();
        Boolean isPrivate = form.getIsPrivate();
        Date createTime = new Date();
        //校验逻辑

        //落库 1.blog表 2.异步增加日志数 3.异步插入时间轴表
        Blog blog = new Blog();
        blog.setUserId(userId);
        String uniqueId = IronUtil.generateUniqueId();
        blog.setUniqueId(uniqueId);
        blog.setTitle(title);
        blog.setCreateTime(createTime);
        blog.setContent(content);
        blog.setPrivate(isPrivate);
        blog.setShare(form.getIsShare());

        blogDAO.save(blog);

        if (form.getIsShare()) {
            String originUniqueId = form.getOriginId();
            if (StringUtils.isEmpty(originUniqueId)) {
                throw new GlobalException(ResponseStatus.PARAM_ERROR, "uniqueId must not be null");
            }
            Blog originBlog = blogDAO.getBaseInfoByUniqueId(originUniqueId);
            if (originBlog == null || originBlog.isPrivate()) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
            Share share = new Share();
            share.setArticleId(blog.getId());
            share.setOriginId(originBlog.getId());
            share.setType(EntityTypeEnum.BLOG.getId());
            share.setDeleted(false);
            share.setCreateTime(createTime);
            shareDAO.save(share);

            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_BLOG,
                    originBlog.getId(), IronConstant.ARTICLE_PROPERTY_SHARE_NUM, true);
        }

        ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_USER,
                userId, IronConstant.USER_PROPERTY_BLOG_NUM, true);

        TimeLine timeLine = new TimeLine();
        timeLine.setUserId(userId);
        timeLine.setArticleId(blog.getId());
        timeLine.setSelf(true);
        timeLine.setNew(true);
        timeLine.setType(EntityTypeEnum.BLOG.getId());
        timeLine.setCreateTime(new Date());
        ansyCommonService.ansyAddTimeLine(timeLine);

        return uniqueId;
    }

    @Override
    public List<BlogAbsVO> pageMyBlogs(PageRequest pageRequest) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        List<Blog> blogList = blogDAO.getAllLimitByUserId(userId, pageRequest);
        List<BlogAbsVO> blogAbsVOList = new ArrayList<>();
        if (blogList != null && blogList.size() != 0) {
            for (Blog blog : blogList) {
                BlogAbsVO blogAbsVO = BeanUtils.copy(blog, BlogAbsVO.class);
                blogAbsVOList.add(blogAbsVO);
            }
        }
        return blogAbsVOList;
    }

    @Override
    public List<BlogAbsVO> pageUserBlogs(String userUniqueId, PageRequest pageRequest) throws GlobalException {
        User user = userDAO.getArticleBaseInfoByUniqueId(userUniqueId);
        if (user == null) {
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        List<Blog> blogList = blogDAO.getAllLimitByUserId(user.getId(), pageRequest);
        List<BlogAbsVO> blogAbsVOList = new ArrayList<>();
        if (blogList != null && blogList.size() != 0) {
            for (Blog blog : blogList) {
                if (!blog.isPrivate()) {
                    BlogAbsVO blogAbsVO = BeanUtils.copy(blog, BlogAbsVO.class);
                    blogAbsVOList.add(blogAbsVO);
                }
            }
        }
        return blogAbsVOList;
    }

    @Override
    public BlogAbsVO assembleBlogAbsVO(Blog blog, User user) throws GlobalException {
        BlogAbsVO blogAbsVO = BeanUtils.copy(blog, BlogAbsVO.class);
        AbstractContent absContent = IronUtil.getAbstractContent(blogAbsVO.getContent(), IronConstant.BLOG_MAX_LENGTH);
        blogAbsVO.setAbstract(absContent.isAbstract());
        blogAbsVO.setContent(absContent.getContent());
        blogAbsVO.setUsername(user.getUsername());
        blogAbsVO.setProfile(user.getProfile());
        Long userId = UserLoginUtil.getLoginUserId();
        LikeLog likeLog = likeLogDAO.getByUserIdAndTargetIdAndType(userId, blog.getId(), EntityTypeEnum.BLOG.getId());
        if (likeLog != null) {
            blogAbsVO.setLikeCondition(likeLog.isLike() ? IronConstant.LIKE_CONDITION_LIKED : IronConstant.LIKE_CONDITION_DISLIKED);
        } else {
            blogAbsVO.setLikeCondition(IronConstant.LIKE_CONDITION_DEFAULT);
        }
        if (blog.isShare()) {
            this.assembleBlogAbsShareInfo(blogAbsVO, blog);
        }
        return blogAbsVO;
    }

    @Override
    public BlogAbsVO assembleBlogAbsVO(Blog blog) throws GlobalException {
        User user = userDAO.getArticleBaseInfoById(blog.getUserId());
        return this.assembleBlogAbsVO(blog, user);
    }

    @Override
    public BlogDetailVO getBlogDetail(String uniqueId) throws GlobalException {
        Blog blog = blogDAO.getByUniqueId(uniqueId);
        Long userId = UserLoginUtil.getLoginUserId();
        if (blog == null) {
            throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
        }
        if (blog.isPrivate() && blog.getUserId() != userId) {
            throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
        }
        BlogDetailVO blogDetailVO = BeanUtils.copy(blog, BlogDetailVO.class);
        User author = userDAO.getArticleBaseInfoById(blog.getUserId());
        blogDetailVO.setUserId(author.getUniqueId());
        blogDetailVO.setUsername(author.getUsername());
        if (blog.isShare()) {
            blogDetailVO.setShare(true);
            Share share = shareDAO.getByArticleIdAndType(blog.getId(), EntityTypeEnum.BLOG.getId());
            if (share == null) {
                log.error(blog.getId() + " 分享信息为空");
                throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
            }
            Blog originBlog = blogDAO.getById(share.getOriginId());
            if (originBlog.isPrivate()) {
                blogDetailVO.setExist(false);
            } else {
                blogDetailVO.setExist(true);
                User originUser = userDAO.getArticleBaseInfoById(originBlog.getUserId());
                blogDetailVO.setOriginUsername(originUser.getUsername());
                blogDetailVO.setOriginUserId(originUser.getUniqueId());
                blogDetailVO.setOriginTitle(originBlog.getTitle());
                blogDetailVO.setOriginContent(originBlog.getContent());
            }
        }


        //执行代理异步添加访问日志
        return blogDetailVO;
    }

    private void assembleBlogAbsShareInfo(BlogAbsVO blogAbsVO, Blog blog) throws GlobalException {
        Share share = shareDAO.getByArticleIdAndType(blog.getId(), EntityTypeEnum.BLOG.getId());
        if (share == null) {
            log.error(blog.getId() + " 分享信息为空");
            throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
        }
        Blog originBlog = blogDAO.getBaseInfoById(share.getOriginId());
        if (originBlog.isPrivate()) {
            blogAbsVO.setExist(false);
        } else {
            blogAbsVO.setExist(true);
            User originUser = userDAO.getArticleBaseInfoById(originBlog.getUserId());
            blogAbsVO.setOriginUsername(originUser.getUsername());
            blogAbsVO.setOriginUserId(originUser.getUniqueId());
            blogAbsVO.setOriginTitle(originBlog.getTitle());
        }
    }
}


