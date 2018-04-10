package com.ironman.forum.dao.es;

import com.ironman.forum.entity.es.EsBlog;
import com.ironman.forum.util.PageRequest;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

@Log4j
public class BlogRepositoryImpl implements CustomBlogRepository {
    /* 搜索模式 */
    private String SCORE_MODE_SUM = "sum"; // 权重分求和模式
    private Float MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Override
    public List<EsBlog> searchBlog(String keyword, PageRequest pageRequest) {
        log.info("searchBlog: keyword [" + keyword + "]");
        // 构建搜索查询
        SearchQuery searchQuery = getBlogSearchQuery(pageRequest.getPage(), pageRequest.getSize(), keyword);
        log.info("searchCity: searchContent [" + keyword + "] \n DSL  = \n " + searchQuery.getQuery().toString());
        Page<EsBlog> blogPage = esBlogRepository.search(searchQuery);
        return blogPage.getContent();
    }


    private SearchQuery getBlogSearchQuery(Integer pageNumber, Integer pageSize, String searchContent) {


        TermQueryBuilder isPrivateQueryBuilder = QueryBuilders.termQuery("is_private",0);
        TermQueryBuilder deletedQueryBuilder = QueryBuilders.termQuery("deleted",0);
        // 短语匹配到的搜索词，求和模式累加权重分
        // 权重分查询 https://www.elastic.co/guide/c ... .html
        //   - 短语匹配 https://www.elastic.co/guide/c ... .html
        //   - 字段对应权重分设置，可以优化成 enum
        //   - 由于无相关性的分值默认为 1 ，设置权重分最小值为 10
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
                .functionScoreQuery()
                .add(QueryBuilders.matchPhraseQuery("title", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(1000))
                .add(QueryBuilders.matchPhraseQuery("content", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                .scoreMode(SCORE_MODE_SUM).setMinScore(MIN_SCORE);
        // 分页参数
        Pageable pageable = new org.springframework.data.domain.PageRequest(pageNumber, pageSize);


        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(isPrivateQueryBuilder).must(deletedQueryBuilder).must(functionScoreQueryBuilder);

        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(boolQueryBuilder).build();
    }
}
