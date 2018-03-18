package com.ironman.forum.dao;

import org.apache.ibatis.annotations.Param;

public interface CommonDAO {
    void changePropertyNumById(@Param("table") String table, @Param("id") long id, @Param("property") String property,
                               @Param("isIncrement") boolean isIncrement);
}
