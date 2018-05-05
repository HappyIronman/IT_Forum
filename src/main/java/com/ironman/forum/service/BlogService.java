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
     * ��װ���ͼ�����Ϣ������չʾ��ʱ����ȴ���
     *
     * @param blog
     * @param user
     * @return
     * @throws GlobalException
     */
    BlogAbsVO assembleBlogAbsVO(Blog blog, User user) throws GlobalException;

    /**
     * ��װ���ͼ�����Ϣ������չʾ��ʱ����ȴ���
     *
     * @param blog the blog
     * @return the blog abs vo
     * @throws GlobalException the global exception
     */
    BlogAbsVO assembleBlogAbsVO(Blog blog) throws GlobalException;

    BlogDetailVO getUserBlogDetail(String uniqueId) throws GlobalException;

    BlogDetailVO getMyBlogDetail(String uniqueId) throws GlobalException;

    List<BlogAbsVO> pageUserBlogs(String userUniqueId, PageRequest pageRequest) throws GlobalException;
}
