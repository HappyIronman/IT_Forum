package com.ironman.forum.conf;

import com.ironman.forum.entity.User;
import com.ironman.forum.util.IronConstant;

/**
 * @Author: Ironman
 * @Description: 获取上下文用户登录信息封装类
 * @Date: Created in 12:16 2017/12/31 0031
 **/
public class UserLoginUtil {
    public static User getLoginUserInfo() {
        Object user = LoginContext.getLoginInfo();
        if (user == null) {
            return null;
        }
        return (User) user;
    }

    public static Long getLoginUserId() {
        User user = getLoginUserInfo();
        return user != null ? user.getId() : IronConstant.ANONYMOUS_USER_ID;
    }

    public static String getLoginUserUniqueId() {
        User user = getLoginUserInfo();
        return user != null ? user.getUniqueId() : IronConstant.ANONYMOUS_USER_UNIQUE_ID;
    }

}
