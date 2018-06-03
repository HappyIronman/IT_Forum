package com.ironman.forum.dao;

import com.ironman.forum.entity.User;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    User getArticleBaseInfoById(Long id);

    User getById(Long id);

    User getByUniqueId(String uniqueId);

    User getByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    User getArticleBaseInfoByUniqueId(String uniqueId);

    User getByPhone(String phone);

    User getByUsername(String username);

    void save(User user);

    void update(User user);

    List<User> pageByUsernameLike(@Param("username") String username, @Param("pageRequest") PageRequest pageRequest);

    int getNewAboutMeNumById(long id);

    void updateNewAboutMeNumById(@Param("id") long id, @Param("value") int value);
}
