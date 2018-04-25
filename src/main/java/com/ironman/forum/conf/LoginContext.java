package com.ironman.forum.conf;

import lombok.extern.log4j.Log4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author: Ironman
 * @Description: 登录信息线程上下文类
 * @Date: Created in 11:49 2017/12/31 0031
 **/
@Log4j
public class LoginContext {

    /**
     * 用于保存用户登录信息
     */
    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void saveLoginInfo(Object object) {
        threadLocal.set(object);
    }

    public static Object getLoginInfo() {
        return threadLocal.get();
    }

    public static void removeLoginInfo() {
        if (threadLocal.get() != null) {
            threadLocal.remove();
            log.info("loginInfo successfully removed.");
        }
    }
}
