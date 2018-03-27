package com.ironman.forum.service;

import com.ironman.forum.form.UserLoginForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.vo.UserVO;

import javax.servlet.http.HttpSession;

public interface UserService {
    UserVO getUserBaseInfo() throws GlobalException;

    void userLogin(UserLoginForm form, HttpSession session) throws GlobalException;
}
