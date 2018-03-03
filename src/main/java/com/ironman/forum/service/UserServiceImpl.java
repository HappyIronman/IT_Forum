package com.ironman.forum.service;

import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.User;
import com.ironman.forum.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private static Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public User getUser(Long id) {
        log.info("user service");
        User user = userDAO.selectUserById(id);
        log.info(Util.toJson(user));
        return user;
    }
}
