package com.ironman.forum.service;

import com.ironman.forum.dao.es.EsBlogRepository;
import com.ironman.forum.entity.EntityType;
import com.ironman.forum.entity.es.EsBlog;
import com.ironman.forum.form.SearchForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.SearchBlogVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Override
    public List<SearchBlogVO> searchBlog(String keywords, PageRequest pageRequest) throws GlobalException {
        List<EsBlog> esBlogList = esBlogRepository.searchBlog(keywords, pageRequest);
        if (esBlogList == null || esBlogList.size() == 0) {
            return new ArrayList<>();
        }

        List<SearchBlogVO> searchBlogVOList = new ArrayList<>(esBlogList.size());

        for (EsBlog esBlog : esBlogList) {
            SearchBlogVO searchBlogVO = BeanUtils.copy(esBlog, SearchBlogVO.class);

            //获取包含keywords的上下文
            String content = StringUtils.isEmpty(esBlog.getContentHighlight()) ? esBlog.getContent() : esBlog.getContentHighlight();
            //去除内容html标签
            content = IronUtil.removeHtmlTags(content);
            //截取指定长度
            content = IronUtil.getSearchAbsContent(content);
            searchBlogVO.setContent(content);

            searchBlogVOList.add(searchBlogVO);
        }
        return searchBlogVOList;
    }

    @Override
    public List<Object> searchEntity(SearchForm form) throws GlobalException {
        //敏感词过滤

        List<Object> entityVOList = new ArrayList<>();
        int type = form.getType();
        int page = form.getPage();
        int size = form.getSize();
        String keywords = form.getKeywords();
        if (StringUtils.isEmpty(keywords)) {
            return entityVOList;
        }
        if (type == EntityType.COMMENT.getId()) {

        } else if (type == EntityType.MOMENT.getId()) {

        } else if (type == EntityType.BLOG.getId()) {
            List<SearchBlogVO> blogVOList = this.searchBlog(keywords, new PageRequest(page, size));
            entityVOList = this.convertToObjectList(blogVOList);
        } else if (type == EntityType.QUESTION.getId()) {

        } else if (type == EntityType.USER.getId()) {

        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }


        //异步插入searchLog
        return entityVOList;
    }

    private <T> List<Object> convertToObjectList(List<T> entityList) {
        if (entityList == null || entityList.size() == 0) {
            return new ArrayList<>();
        }

        List<Object> objectList = new ArrayList<>(entityList.size());
        Collections.addAll(objectList, entityList);

        return objectList;
    }

}
