package com.ironman.forum.test;

import com.ironman.forum.service.RecommendService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecommendTest extends BaseJunit4Test {
    @Autowired
    private RecommendService recommendService;

    @Test
    public void test01() throws GlobalException {
        recommendService.getRecommendList(new PageRequest(0, 5));
    }
}
