package com.ironman.forum.service;

import com.ironman.forum.entity.Blog;
import com.ironman.forum.entity.User;
import com.ironman.forum.form.BlogPublishForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.BlogAbsVO;
import com.ironman.forum.vo.BlogDetailVO;

import java.util.List;

public interface BlogService {
    /**
     * Publish blog string.
     *
     * @param form the form
     * @return the string
     * @throws GlobalException the global exception
     */
    String publishBlog(BlogPublishForm form) throws GlobalException;

    /**
     * Page my blogs list.
     *
     * @param pageRequest the page request
     * @return the list
     * @throws GlobalException the global exception
     */
    List<BlogAbsVO> pageMyBlogs(PageRequest pageRequest) throws GlobalException;

    /**
     * 组装博客简略信息（用来展示在时间轴等处）
     *
     * @param blog
     * @param user
     * @return
     * @throws GlobalException
     */
    BlogAbsVO assembleBlogAbsVO(Blog blog, User user) throws GlobalException;

    /**
     * 组装博客简略信息（用来展示在时间轴等处）
     *
     * @param blog the blog
     * @return the blog abs vo
     * @throws GlobalException the global exception
     */
    BlogAbsVO assembleBlogAbsVO(Blog blog) throws GlobalException;

    BlogDetailVO getBlogDetail(String uniqueId) throws GlobalException;

    List<BlogAbsVO> pageUserBlogs(String userUniqueId, PageRequest pageRequest) throws GlobalException;
}
