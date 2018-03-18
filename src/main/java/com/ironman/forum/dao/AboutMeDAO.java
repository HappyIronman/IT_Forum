package com.ironman.forum.dao;

import com.ironman.forum.entity.AboutMe;
import org.apache.ibatis.annotations.Param;

public interface AboutMeDAO {
    void save(AboutMe aboutMe);

    void deleteByLogIdAndType(@Param("logId") long logId, @Param("type") int type);
}
