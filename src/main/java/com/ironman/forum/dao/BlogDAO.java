package com.ironman.forum.dao;

import com.ironman.forum.entity.Blog;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogDAO {
    void save(Blog blog);

    Blog getById(long id);

    Blog getBaseInfoById(long id);

    Blog getBaseInfoByUniqueId(String uniqueId);

    Blog getByUniqueId(String uniqueId);

    List<Blog> getAllLimitByUserId(@Param("userId") Long userId, @Param("pageRequest") PageRequest pageRequest);

    List<Blog> getPublicLimitByUserId(@Param("userId") Long userId, @Param("pageRequest") PageRequest pageRequest);
}
