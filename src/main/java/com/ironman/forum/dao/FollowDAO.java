package com.ironman.forum.dao;

import com.ironman.forum.entity.Follow;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowDAO {
    Follow getByFollowerIdAndUserId(@Param("followerId") long followerId, @Param("targetId") long targetId);

    void save(Follow follow);

    List<Follow> getAllLimitByUserId(@Param("userId") Long userId, @Param("pageRequest") PageRequest pageRequest);

    List<Follow> getAllLimitByFollowerId(@Param("followerId") Long followerId,
                                         @Param("pageRequest") PageRequest pageRequest);

    Follow getById(long id);
}
