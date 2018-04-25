package com.ironman.forum.service.aop;

import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronUtil;
import com.ironman.forum.util.ResponseBean;
import com.ironman.forum.util.ResponseStatus;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Log4j
public class WebExceptionAspect {

    @Value("#{prop.permitted_origin_host}")
    private String permittedOriginHost;

    public void handleThrowing(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            writeContent(new ResponseBean(globalException.getResponseStatus()));
        } else {
            writeContent(new ResponseBean(ResponseStatus.SYSTEM_ERROR));
        }
    }

    /**
     * 将内容输出到浏览器
     */
    private void writeContent(ResponseBean responseBean) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", this.permittedOriginHost);
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, content-type, Accept");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(IronUtil.toJson(responseBean));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
    }
}
