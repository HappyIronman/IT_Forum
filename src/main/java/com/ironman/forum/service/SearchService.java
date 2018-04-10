package com.ironman.forum.service;

import com.ironman.forum.form.SearchForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.SearchBlogVO;

import java.util.List;

public interface SearchService {
    List<SearchBlogVO> searchBlog(String keywords, PageRequest pageRequest) throws GlobalException;

    List<Object> searchEntity(SearchForm form) throws GlobalException;
}
