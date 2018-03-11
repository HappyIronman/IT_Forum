package com.ironman.forum.service;

import com.ironman.forum.entity.Blog;
import com.ironman.forum.entity.User;
import com.ironman.forum.form.BlogPublishForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.BlogVO;

import java.util.List;

public interface BlogService {
    String publishBlog(BlogPublishForm form) throws GlobalException;

    List<BlogVO> pageMyBlogs(PageRequest pageRequest) throws GlobalException;

    BlogVO assembleBlogVO(Blog blog, User user) throws GlobalException;

    BlogVO assembleBlogVO(Blog blog) throws GlobalException;
}
