package com.ironman.forum.test;

import com.ironman.forum.entity.EntityTypeEnum;
import com.ironman.forum.form.SearchForm;
import com.ironman.forum.service.SearchService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EsTest extends BaseJunit4Test {

    @Autowired
    private SearchService searchService;

    @Test
    public void test01() throws GlobalException {
        SearchForm searchForm = new SearchForm();
        searchForm.setKeywords("数据绑定");
        searchForm.setType(EntityTypeEnum.BLOG.getId());
        searchForm.setPage(1);
        searchForm.setSize(3);
        List<Object> searchBlogVOList = searchService.searchEntity(searchForm);
        System.out.println(IronUtil.toJson(searchBlogVOList));
    }
}
