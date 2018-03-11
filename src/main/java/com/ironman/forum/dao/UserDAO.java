package com.ironman.forum.dao;

import com.ironman.forum.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {
    User getArticleBaseInfoById(Long id);
}
