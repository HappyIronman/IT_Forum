package com.ironman.forum.service;

import com.ironman.forum.dao.es.EsBlogRepository;
import com.ironman.forum.entity.ArticleTypeEnum;
import com.ironman.forum.entity.es.EsBlog;
import com.ironman.forum.form.SearchForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.SearchBlogVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

            //��ȡ����keywords��������
            String content = StringUtils.isEmpty(esBlog.getContentHighlight()) ?
                    esBlog.getContent() : esBlog.getContentHighlight();
            //ȥ������html��ǩ
            content = IronUtil.removeHtmlTags(content);
            //��ȡָ������
            content = IronUtil.getSearchAbsContent(content);
            searchBlogVO.setContent(content);

            searchBlogVOList.add(searchBlogVO);
        }
        return searchBlogVOList;
    }

    @Override
    public List<Object> searchEntity(SearchForm form) throws GlobalException {
        //���дʹ���

        List<Object> entityVOList = new ArrayList<>();
        int type = form.getType();
        int page = form.getPage();
        int size = form.getSize();
        String keywords = form.getKeywords();
        if (StringUtils.isEmpty(keywords)) {
            return entityVOList;
        }
        if (type == ArticleTypeEnum.COMMENT.getId()) {

        } else if (type == ArticleTypeEnum.MOMENT.getId()) {

        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            List<SearchBlogVO> blogVOList = this.searchBlog(keywords, new PageRequest(page, size));
            entityVOList = this.convertToObjectList(blogVOList);
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {

        } else if (type == ArticleTypeEnum.USER.getId()) {

        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }


        //�첽����searchLog
        return entityVOList;
    }

    private <T> List<Object> convertToObjectList(List<T> entityList) {
        if (entityList == null || entityList.size() == 0) {
            return new ArrayList<>();
        }

        List<Object> objectList = new ArrayList<>(entityList.size());
        for (T entity : entityList) {
            objectList.add(entity);
        }

        return objectList;
    }

}
