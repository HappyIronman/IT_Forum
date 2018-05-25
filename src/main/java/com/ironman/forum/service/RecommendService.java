package com.ironman.forum.service;


import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.RecommendItemVO;

import java.util.List;

public interface RecommendService {
    List<RecommendItemVO> getRecommendList(PageRequest pageRequest) throws GlobalException;
}
