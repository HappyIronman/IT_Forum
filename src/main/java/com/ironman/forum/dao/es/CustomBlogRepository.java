package com.ironman.forum.dao.es;

import com.ironman.forum.entity.es.EsBlog;
import com.ironman.forum.util.PageRequest;

import java.util.List;

public interface CustomBlogRepository {
    List<EsBlog> searchBlog(String keyword, PageRequest pageRequest);
}
