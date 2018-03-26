package com.ironman.forum.service;

import com.ironman.forum.util.GlobalException;
import com.ironman.forum.vo.UserVO;

public interface UserService {
    UserVO getUserBaseInfo() throws GlobalException;
}
