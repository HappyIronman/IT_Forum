package com.ironman.forum.dao;

import com.ironman.forum.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
    User getArticleBaseInfoById(Long id);

    User getById(Long id);

    User getByUniqueId(String uniqueId);

    User getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User getArticleBaseInfoByUniqueId(String uniqueId);

    User getByPhone(String phone);

    void save(User user);

    void update(User user);
}
