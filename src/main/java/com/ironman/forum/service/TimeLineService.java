package com.ironman.forum.service;

import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.TimeLineVO;

import java.util.List;

public interface TimeLineService {
    List<TimeLineVO> pageMyFriendCircle(PageRequest pageRequest) throws GlobalException;
}
