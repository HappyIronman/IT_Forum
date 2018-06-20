package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.BlogDAO;
import com.ironman.forum.dao.ShareDAO;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.ArticleTypeEnum;
import com.ironman.forum.entity.Blog;
import com.ironman.forum.entity.Share;
import com.ironman.forum.entity.User;
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
    private AnsyService ansyService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ShareDAO shareDAO;

    @Override
    @Transactional
    public String publishBlog(BlogPublishForm form) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        String title = form.getTitle();
        String content = form.getContent();
        Boolean isPrivate = form.getIsPrivate();
        Date createTime = new Date();

        //??? 1.blog?? 2.??????????? 3.????????????
        Blog blog = new Blog();
        blog.setUserId(userId);
        String uniqueId = IronUtil.generateUniqueId();
        blog.setUniqueId(uniqueId);
        blog.setCreateTime(createTime);
        blog.setContent(content);
        blog.setPrivate(isPrivate);
        blog.setShare(form.getIsShare());
        //若为转载，则指定标题
        Blog originBlog = null;
        if (form.getIsShare()) {
            String originUniqueId = form.getOriginId();
            if (StringUtils.isEmpty(originUniqueId)) {
                throw new GlobalException(ResponseStatus.PARAM_ERROR, "uniqueId must not be null");
            }
            originBlog = blogDAO.getBaseInfoByUniqueId(originUniqueId);
            if (originBlog == null) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
            blog.setTitle("转发:" + originBlog.getTitle());
        } else {
            blog.setTitle(title);
        }

        blogDAO.save(blog);

        if (form.getIsShare()) {
            //originBlog一定不为空
            assert originBlog != null;
            Share share = new Share();
            share.setArticleId(blog.getId());
            share.setOriginId(originBlog.getId());
            share.setType(ArticleTypeEnum.BLOG.getId());
            share.setDeleted(false);
            share.setCreateTime(createTime);
            shareDAO.save(share);

            ansyService.ansyChangeArticlePropertyNum(ArticleTypeEnum.BLOG.getId(),
                    originBlog.getId(), IronConstant.ARTICLE_PROPERTY_SHARE_NUM, true);
        }

        ansyService.ansyChangeUserPropertyNum(userId, IronConstant.USER_PROPERTY_BLOG_NUM, true);

        //如果不是私人权限，异步插入时间轴
        if (!isPrivate) {
            ansyService.ansyAddTimeLine(userId, blog.getId(), ArticleTypeEnum.BLOG.getId());
        }

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
        List<Blog> blogList = blogDAO.getPublicLimitByUserId(user.getId(), pageRequest);
        List<BlogAbsVO> blogAbsVOList = new ArrayList<>();
        if (blogList != null && blogList.size() != 0) {
            for (Blog blog : blogList) {
                if (!blog.isPrivate()) {
                    BlogAbsVO blogAbsVO = BeanUtils.copy(blog, BlogAbsVO.class);
                    blogAbsVO.setUserId(user.getUniqueId());
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
        blogAbsVO.setUserId(user.getUniqueId());
        blogAbsVO.setUsername(user.getUsername());
        blogAbsVO.setProfileUrl(commonService.concatImageUrl(user.getProfile()));
        blogAbsVO.setLikeCondition(commonService.judgeLikeCondition(blog));

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
    public BlogDetailVO getUserBlogDetail(String uniqueId) throws GlobalException {
        Blog blog = blogDAO.getByUniqueId(uniqueId);
        Long userId = UserLoginUtil.getLoginUserId();
        if (blog == null) {
            throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
        }
        if (blog.isPrivate() && blog.getUserId() != userId) {
            throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
        }
        return this.assembleBlogDetailVO(blog);

    }

    @Override
    public BlogDetailVO getMyBlogDetail(String uniqueId) throws GlobalException {
        Blog blog = blogDAO.getByUniqueId(uniqueId);
        Long userId = UserLoginUtil.getLoginUserId();
        if (blog == null || blog.getUserId() != userId) {
            throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
        }
        return this.assembleBlogDetailVO(blog);
    }

    private BlogDetailVO assembleBlogDetailVO(Blog blog) throws GlobalException {
        BlogDetailVO blogDetailVO = BeanUtils.copy(blog, BlogDetailVO.class);
        User author = userDAO.getArticleBaseInfoById(blog.getUserId());
        blogDetailVO.setUserId(author.getUniqueId());
        blogDetailVO.setUsername(author.getUsername());
        blogDetailVO.setLikeCondition(commonService.judgeLikeCondition(blog));
        if (blog.isShare()) {
            blogDetailVO.setShare(true);
            Share share = shareDAO.getByArticleIdAndType(blog.getId(), ArticleTypeEnum.BLOG.getId());
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
                blogDetailVO.setOriginCreateTime(originBlog.getCreateTime());
            }
        }


        return blogDetailVO;
    }

    private void assembleBlogAbsShareInfo(BlogAbsVO blogAbsVO, Blog blog) throws GlobalException {
        Share share = shareDAO.getByArticleIdAndType(blog.getId(), ArticleTypeEnum.BLOG.getId());
        if (share == null) {
            log.error(blog.getId() + "分享信息不存在");
            throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
        }
        Blog originBlog = blogDAO.getBaseInfoById(share.getOriginId());
        if (originBlog.isPrivate()) {
            blogAbsVO.setExist(false);
        } else {
            blogAbsVO.setExist(true);
            User originUser = userDAO.getArticleBaseInfoById(originBlog.getUserId());
            blogAbsVO.setOriginUserId(originUser.getUniqueId());
            blogAbsVO.setOriginUsername(originUser.getUsername());
            blogAbsVO.setOriginUserId(originUser.getUniqueId());
            blogAbsVO.setOriginBlogId(originBlog.getUniqueId());
            blogAbsVO.setOriginTitle(originBlog.getTitle());
            blogAbsVO.setOriginCreateTime(originBlog.getCreateTime());
        }
    }
}


