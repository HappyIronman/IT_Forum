package com.ironman.forum.service;

import com.ironman.forum.dao.BlogDAO;
import com.ironman.forum.dao.ShareDAO;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.*;
import com.ironman.forum.form.BlogPublishForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.BlogVO;
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
    private CommonService commonService;


    @Override
    @Transactional
    public String publishBlog(BlogPublishForm form) throws GlobalException {
        Long userId = 123L;
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
            Blog originBlog = blogDAO.getByUniqueId(originUniqueId);
            if (originBlog == null || originBlog.isPrivate()) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
            Share share = new Share();
            share.setArticleId(blog.getId());
            share.setOriginId(originBlog.getId());
            share.setType(ArticleType.BLOG.getId());
            share.setDeleted(false);
            share.setCreateTime(createTime);
            shareDAO.save(share);

            commonService.ansyChangeEntityPropertyNum(IronConstant.TABLE_BLOG,
                    originBlog.getId(), IronConstant.BLOG_PROPERTY_SHARE_NUM, true);
        }

        commonService.ansyChangeEntityPropertyNum(IronConstant.TABLE_USER,
                userId, IronConstant.USER_PROPERTY_BLOG_NUM, true);

        TimeLine timeLine = new TimeLine();
        timeLine.setUserId(userId);
        timeLine.setEventId(blog.getId());
        timeLine.setSelf(true);
        timeLine.setNew(true);
        timeLine.setType(ArticleType.BLOG.getId());
        timeLine.setCreateTime(new Date());
        commonService.ansyAddTimeLine(timeLine);

        return uniqueId;
    }

    @Override
    public List<BlogVO> pageMyBlogs(PageRequest pageRequest) throws GlobalException {
        Long userId = 123L;
        List<Blog> blogList = blogDAO.getAllLimitByUserId(userId, pageRequest);
        List<BlogVO> blogVOList = new ArrayList<>();
        if (blogList != null && blogList.size() != 0) {
            for (Blog blog : blogList) {
                BlogVO blogVO = new BlogVO();
                blogVO.setUniqueId(blog.getUniqueId());
                blogVO.setTitle(blog.getTitle());
                blog.setCommentNum(blog.getCommentNum());
                blogVO.setViewNum(blog.getViewNum());
                blogVO.setCreateTime(blog.getCreateTime());
                blogVO.setPrivate(blog.isPrivate());
                blogVOList.add(blogVO);
            }
        }
        return blogVOList;
    }

    @Override
    public BlogVO assembleBlogVO(Blog blog, User user) throws GlobalException {
        BlogVO blogVO = BeanUtils.copy(blog, BlogVO.class);
        AbstractContent absContent = IronUtil.getAbstractContent(blogVO.getContent(), IronConstant.BLOG_MAX_LENGTH);
        blogVO.setAbstract(absContent.isAbstract());
        blogVO.setContent(absContent.getContent());
        blogVO.setUsername(user.getUsername());
        blogVO.setProfile(user.getProfile());
        if (blog.isShare()) {
            this.assembleBlogShareInfo(blogVO, blog);
        }
        return blogVO;
    }

    @Override
    public BlogVO assembleBlogVO(Blog blog) throws GlobalException {
        User user = userDAO.getArticleBaseInfoById(blog.getUserId());
        return this.assembleBlogVO(blog, user);
    }

    private void assembleBlogShareInfo(BlogVO blogVO, Blog blog) throws GlobalException {
        Share share = shareDAO.getByArticleIdAndType(blog.getId(), ArticleType.BLOG.getId());
        if (share == null) {
            log.error(blog.getId() + " 分享信息为空");
            throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
        }
        Blog originBlog = blogDAO.getById(share.getOriginId());
        if (originBlog.isPrivate()) {
            blogVO.setExist(false);
        } else {
            blogVO.setExist(true);
            User originUser = userDAO.getArticleBaseInfoById(originBlog.getUserId());
            blogVO.setOriginUsername(originUser.getUsername());
            blogVO.setOriginUserId(originUser.getUniqueId());
            blogVO.setOriginTitle(originBlog.getTitle());
        }
    }
}


