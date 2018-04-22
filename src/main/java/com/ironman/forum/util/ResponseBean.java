package com.ironman.forum.util;

public class ResponseBean {
    private Object responseVO;
    private Integer code;
    private String msg;

    public ResponseBean(ResponseStatus responseStatus, Object responseVO) {
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
        this.responseVO = responseVO;
    }

    public ResponseBean(ResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
    }

    public ResponseBean(ResponseStatus responseStatus, String msg) {
        this.code = responseStatus.getCode();
        this.msg = msg;
    }

    public Object getResponseVO() {
        return responseVO;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
