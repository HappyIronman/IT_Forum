package com.ironman.forum.dao;

import com.ironman.forum.entity.TimeLine;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimeLineDAO {
    void save(TimeLine timeLine);

    void batchSave(@Param("timeLineList") List<TimeLine> timeLineList);

    List<TimeLine> getAllLimitByUserId(@Param("userId") Long userId, @Param("pageRequest") PageRequest pageRequest);
}
