package com.ironman.forum.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class ResponseBean {
    private Object responseVO;
    @JsonIgnore
    private HttpStatus httpStatus;
    private Integer code;
    private String msg;

    public ResponseBean(ResponseStatus responseStatus, Object responseVO) {
        this.httpStatus = responseStatus.getHttpStatus();
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
        this.responseVO = responseVO;
    }

    public ResponseBean(ResponseStatus responseStatus) {
        this.httpStatus = responseStatus.getHttpStatus();
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
    }

    public ResponseBean(ResponseStatus responseStatus, String msg) {
        this.httpStatus = responseStatus.getHttpStatus();
        this.code = responseStatus.getCode();
        this.msg = msg;
    }

    public Object getResponseVO() {
        return responseVO;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
