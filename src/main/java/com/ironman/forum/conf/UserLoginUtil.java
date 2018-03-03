package com.ironman.forum.conf;

import com.ironman.forum.entity.User;

/**
 * @Author: Ironman
 * @Description:
 * @Date: Created in 12:16 2017/12/31 0031
 **/
public class UserLoginUtil {
	public static User getLoginUserInfo(){
		Object user = LoginContext.getLoginInfo();
		return (User) user;
	}

	public static Long getLoginUserId(){
		return getLoginUserInfo().getId();
	}

}
