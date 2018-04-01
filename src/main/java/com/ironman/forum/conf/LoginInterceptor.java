package com.ironman.forum.conf;

import com.ironman.forum.entity.User;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.IronUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Ironman
 * @Description:
 * @Date: Created in 11:14 2017/12/31 0031
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static Log logger = LogFactory.getLog(LoginInterceptor.class);

    @Value("#{prop.permitted_origin_host}")
    private String permittedOriginHost;

    //url和权限对应表
    private RegexHashMap<String, Set<String>> restrictUrlMap;

    private RegexSet<String> publicUrlSet;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setHeader("Access-Control-Allow-Origin", this.permittedOriginHost);
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, content-type, Accept");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        if (httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return true;
        }

        //测试环境直接放行
//        if (true) {
//            return true;
//        }

        String requestUri = httpServletRequest.getRequestURI();
        if (publicUrlSet.contains(requestUri)) {
            logger.info("访问公开url:" + requestUri);
            return true;
        }

        logger.info("当前访问url:" + requestUri);
        HttpSession session = httpServletRequest.getSession();
        logger.info(IronUtil.toJson(session));
        User user = (User) session.getAttribute(IronConstant.SESSION_USER_KEY);
        if (user != null) {
            logger.info(user.toString());
            List<String> userRoles = (List<String>) session.getAttribute(IronConstant.SESSION_ROLE_KEY);
            if (userRoles == null) {
                logger.error(user.getId() + "权限为空");
                return false;
            }
            logger.info("用户权限: " + IronUtil.toJson(userRoles));
            Set<String> permittedRoles = this.restrictUrlMap.get(requestUri);
            //默认为用户权限
            if (permittedRoles == null) {
                permittedRoles = new HashSet<>();
                permittedRoles.add("ROLE_USER");
            }
            logger.info("所需权限: " + IronUtil.toJson(permittedRoles));
            for (String role : userRoles) {
                if (permittedRoles.contains(role)) {
                    LoginContext.saveLoginInfo(user);
                    return true;
                }
            }
            logger.error(user.getId() + "无权限");
            httpServletResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
            httpServletResponse.getWriter().write("403无权限");
            return false;
        } else {
            logger.error("session中用户信息为空");
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        LoginContext.removeLoginInfo();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public RegexHashMap<String, Set<String>> getRestrictUrlMap() {
        return restrictUrlMap;
    }

    public void setRestrictUrlMap(RegexHashMap<String, Set<String>> restrictUrlMap) {
        this.restrictUrlMap = restrictUrlMap;
    }

    public RegexSet<String> getPublicUrlSet() {
        return publicUrlSet;
    }

    public void setPublicUrlSet(RegexSet<String> publicUrlSet) {
        this.publicUrlSet = publicUrlSet;
    }

    public void setPermittedOriginHost(String permittedOriginHost) {
        this.permittedOriginHost = permittedOriginHost;
    }
}
