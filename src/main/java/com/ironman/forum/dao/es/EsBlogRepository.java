package com.ironman.forum.dao.es;

import com.ironman.forum.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Long>, CustomBlogRepository {
}
