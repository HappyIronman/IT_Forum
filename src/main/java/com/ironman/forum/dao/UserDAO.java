package com.ironman.forum.dao;

import com.ironman.forum.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
    User getMomentInfoById(Long id);

    void changeMomentNum(@Param("userId") long userId, @Param("isIncrement") boolean isIncrement);
}
