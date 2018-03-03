package com.ironman.forum.conf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author: Ironman
 * @Description:
 * @Date: Created in 11:49 2017/12/31 0031
 **/
public class LoginContext {
	private static Log logger = LogFactory.getLog(LoginContext.class);

	private static final ThreadLocal<Object> threadLocal = new ThreadLocal<>();

	public static void saveLoginInfo(Object object){
		threadLocal.set(object);
	}
	public static Object getLoginInfo(){
		return threadLocal.get();
	}
	public static void removeLoginInfo(){
		if(threadLocal.get()!=null){
			threadLocal.remove();
			logger.info("loginInfo successfully removed.");
		}
	}
}
