package com.ironman.forum.dao.es;

import com.ironman.forum.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BlogRepository extends ElasticsearchRepository<EsBlog, Long>, CustomBlogRepository{
}
