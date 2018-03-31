package com.ironman.forum.dao;

import com.ironman.forum.entity.ViewLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ViewLogDAO {
    ViewLog getById(long id);

    void batchSave(@Param("viewLogList") List<ViewLog> viewLogList);

    void save(ViewLog viewLog);
}
