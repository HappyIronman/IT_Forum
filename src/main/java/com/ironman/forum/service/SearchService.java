package com.ironman.forum.service;

import com.ironman.forum.form.SearchForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.SearchBlogVO;
import com.ironman.forum.vo.SearchUserVO;

import java.util.List;

public interface SearchService {
    List<SearchBlogVO> searchBlog(String keywords, PageRequest pageRequest) throws GlobalException;

    List<SearchUserVO> searchUser(String keywords, PageRequest pageRequest);

    List<Object> searchEntity(SearchForm form) throws GlobalException;

    void ansyUpdateSearchLog(long userId, String keyword, int type);
}
