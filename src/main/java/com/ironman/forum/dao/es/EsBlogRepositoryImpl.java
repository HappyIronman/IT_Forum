package com.ironman.forum.dao.es;

import com.ironman.forum.entity.es.EsBlog;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.PageRequest;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j
public class EsBlogRepositoryImpl implements CustomBlogRepository {
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

        elasticsearchTemplate.refresh(EsBlog.class);
        Page<EsBlog> blogPage = elasticsearchTemplate.queryForPage(searchQuery, EsBlog.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {

                List<EsBlog> esBlogList = new ArrayList<EsBlog>();
                for (SearchHit searchHit : response.getHits()) {
                    if (response.getHits().getHits().length <= 0) {
                        return null;
                    }

                    Map<String, Object> hitMap = searchHit.getSource();

                    EsBlog esBlog = new EsBlog();
                    esBlog.setId((int) hitMap.get("id"));
                    esBlog.setUniqueId((String) hitMap.get("unique_id"));
                    esBlog.setUserId((int) hitMap.get("user_id"));
                    esBlog.setUserUniqueId((String) hitMap.get("user_unique_id"));
                    esBlog.setUsername((String) hitMap.get("username"));
                    esBlog.setTitle((String) hitMap.get("title"));
                    esBlog.setContent((String) hitMap.get("content"));
                    esBlog.setCommentNum((int) hitMap.get("comment_num"));
                    esBlog.setLikeNum((int) hitMap.get("like_num"));
                    esBlog.setDislikeNum((int) hitMap.get("dislike_num"));
                    esBlog.setViewNum((int) hitMap.get("view_num"));
                    esBlog.setShareNum((int) hitMap.get("share_num"));
                    esBlog.setShare((int) hitMap.get("is_share") == 1);
                    try {
                        esBlog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse((String) hitMap.get("create_time")));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }


                    Map<String, HighlightField> highlightFieldMap = searchHit.getHighlightFields();
                    if (highlightFieldMap != null) {
                        if (highlightFieldMap.get("title") != null) {
                            esBlog.setTitleHighlight(highlightFieldMap.get("title").fragments()[0].toString());
                        }
                        if (highlightFieldMap.get("content") != null) {
                            esBlog.setContentHighlight(highlightFieldMap.get("content").fragments()[0].toString());
                        }
                    }

                    esBlogList.add(esBlog);
                }
                if (esBlogList.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) esBlogList);
                }
                return null;
            }
        });

//        Page<EsBlog> blogPage = esBlogRepository.search(searchQuery);
        if (blogPage == null) {
            return new ArrayList<>();
        }
        return blogPage.getContent();
    }


    private SearchQuery getBlogSearchQuery(Integer pageNumber, Integer pageSize, String searchContent) {


        MatchQueryBuilder titleQueryBuilder = QueryBuilders.matchPhraseQuery("title", searchContent);
        MatchQueryBuilder contentQueryBuilder = QueryBuilders.matchPhraseQuery("content", searchContent);

        TermQueryBuilder deletedQueryBuilder = QueryBuilders.termQuery("deleted", 0);
        TermQueryBuilder isPrivateQueryBuilder = QueryBuilders.termQuery("is_private", 0);
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
        boolQueryBuilder
                .must(isPrivateQueryBuilder)
                .must(deletedQueryBuilder)
                .should(titleQueryBuilder)
                .should(contentQueryBuilder)
                .filter(functionScoreQueryBuilder);

//
//        SearchRequestBuilder request = elasticsearchTemplate.getClient().prepareSearch("blog")
//                .setQuery(boolQueryBuilder)
//                .addHighlightedField("title")
//                .addHighlightedField("content")
//                .setHighlighterPreTags("<hahaha>")
//                .setHighlighterPostTags("</hehehe>");


        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(boolQueryBuilder)
                .withHighlightFields(new HighlightBuilder.Field("title").preTags(IronConstant.ES_PRE_TAGS).postTags(IronConstant.ES_POST_TAGS),
                        new HighlightBuilder.Field("content").preTags(IronConstant.ES_PRE_TAGS).postTags(IronConstant.ES_POST_TAGS).fragmentSize(IronConstant.ES_HIGHLIGHT_MAX_SIZE))
                .build();
    }
}
