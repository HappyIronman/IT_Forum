package com.ironman.forum.dao;

import com.ironman.forum.entity.UserMoment;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMomentDAO {
    void save(UserMoment userMoment);

    List<UserMoment> getAllLimitByUserId(@Param("userId") Long userId, @Param("pageRequest") PageRequest pageRequest);
}
