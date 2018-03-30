package com.ironman.forum.dao;

import com.ironman.forum.entity.AboutMe;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AboutMeDAO {
    void save(AboutMe aboutMe);

    void deleteByLogIdAndType(@Param("logId") long logId, @Param("type") int type);

    List<AboutMe> getAllLimitByUserId(@Param("userId") long userId, @Param("pageRequest") PageRequest pageRequest);
}
