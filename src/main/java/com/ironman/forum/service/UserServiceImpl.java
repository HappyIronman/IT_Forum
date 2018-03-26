package com.ironman.forum.service;

import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.User;
import com.ironman.forum.util.BeanUtils;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.UserVO;
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
    public UserVO getUserBaseInfo() throws GlobalException {
        Long userId = 123L;
        User user = userDAO.getById(userId);
        if (user == null) {
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        UserVO userVO = BeanUtils.copy(user, UserVO.class);
        return userVO;
    }
}
