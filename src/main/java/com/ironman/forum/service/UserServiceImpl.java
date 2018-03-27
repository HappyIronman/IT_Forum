package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.User;
import com.ironman.forum.form.UserLoginForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.UserVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private static Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserVO getUserBaseInfo() throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        User user = userDAO.getById(userId);
        if (user == null) {
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        UserVO userVO = BeanUtils.copy(user, UserVO.class);
        return userVO;
    }


    @Override
    public void userLogin(UserLoginForm form, HttpSession session) throws GlobalException {
        User user = userDAO.getByUsernameAndPassword(form.getUsername(), form.getPassword());
        if (user == null) {
            throw new GlobalException(ResponseStatus.USERNAME_OR_PASSWORD_INCORRECT);
        }
        session.setAttribute(IronConstant.SESSION_USER_KEY, user);
        session.setAttribute(IronConstant.SESSION_ROLE_KEY, Arrays.asList("ROLE_USER"));
        log.info(IronUtil.toJson(session));
    }
}
