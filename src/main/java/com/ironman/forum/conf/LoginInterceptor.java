package com.ironman.forum.conf;

import com.ironman.forum.entity.User;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.IronUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Ironman
 * @Description: 用户登录及其权限校验拦截器
 * @Date: Created in 11:14 2017/12/31 0031
 **/
@Component
@Log4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 允许发起跨域请求的域名数组
     */
    @Value("#{prop.permitted_origin_hosts}")
    private String permittedOriginHostStr;

    private Set<String> permittedOriginHostSet;

    /**
     * 受限url和权限对应map，默认为用户权限
     */
    private RegexHashMap<String, Set<String>> restrictUrlMap;

    /**
     * 可公开访问url集合，若添加新的公开接口，必须在此配置
     */
    private RegexSet<String> publicUrlSet;

    @PostConstruct
    public void init() {
        log.info("LoginInterceptor初始化permittedOriginHostSet");
        String[] permittedOriginHostArr = this.permittedOriginHostStr.split(";");
        this.permittedOriginHostSet = new HashSet<>(Arrays.asList(permittedOriginHostArr));
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String originHeader = httpServletRequest.getHeader("Origin");
        if (permittedOriginHostSet.contains(originHeader)) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", originHeader);
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setHeader("Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, content-type, Accept");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

            if (httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.toString())) {
                httpServletResponse.setStatus(HttpStatus.OK.value());
                return true;
            }
        }


        String requestUri = httpServletRequest.getRequestURI();

        if (publicUrlSet.contains(requestUri)) {
            log.info("访问公开url:" + requestUri);
            HttpSession session = httpServletRequest.getSession();
            User user = (User) session.getAttribute(IronConstant.SESSION_USER_KEY);
            if (user != null) {
                LoginContext.saveLoginInfo(user);
            }
            return true;
        }

        log.info("当前访问url:" + requestUri);
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(IronConstant.SESSION_USER_KEY);
        if (user != null) {
            List<String> userRoles = (List<String>) session.getAttribute(IronConstant.SESSION_ROLE_KEY);
            if (userRoles == null) {
                log.error(user.getId() + "权限为空");
                return false;
            }
            log.info("用户权限: " + IronUtil.toJson(userRoles));
            Set<String> permittedRoles = this.restrictUrlMap.get(requestUri);
            //默认为用户权限
            if (permittedRoles == null) {
                permittedRoles = new HashSet<>();
                permittedRoles.add("ROLE_USER");
            }
            log.info("所需权限: " + IronUtil.toJson(permittedRoles));
            for (String role : userRoles) {
                if (permittedRoles.contains(role)) {
                    LoginContext.saveLoginInfo(user);
                    return true;
                }
            }
            log.error(user.getId() + "无权限");
            httpServletResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
            httpServletResponse.getWriter().write("403无权限");
            return false;
        } else {
            log.error("session中用户信息为空");
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

    public void setPermittedOriginHostStr(String permittedOriginHostStr) {
        this.permittedOriginHostStr = permittedOriginHostStr;
    }
}
