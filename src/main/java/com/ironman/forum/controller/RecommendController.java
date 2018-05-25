package com.ironman.forum.controller;

import com.ironman.forum.service.RecommendService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronResponseEntity;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.RecommendItemVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 个性化推荐列表控制器
 */
@Log4j
@RestController
@RequestMapping("/data")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @RequestMapping(value = "/recommend/homepage/", method = RequestMethod.GET)
    public IronResponseEntity getHomepageRecommendList(PageRequest pageRequest) throws GlobalException {
        List<RecommendItemVO> recommendItemVOList = recommendService.getRecommendList(pageRequest);
        return new IronResponseEntity(ResponseStatus.SUCCESS, recommendItemVOList);
    }
}
