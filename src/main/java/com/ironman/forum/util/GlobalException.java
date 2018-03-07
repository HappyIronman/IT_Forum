package com.ironman.forum.util;


public class GlobalException extends Exception {
    protected ResponseStatus responseStatus;

    public GlobalException(ResponseStatus responseStatus) {
        super(responseStatus.getMsg());
        this.responseStatus = responseStatus;
    }

    public GlobalException(ResponseStatus responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public GlobalException() {
        responseStatus = ResponseStatus.SYSTEM_ERROR;
    }

    public GlobalException(String message) {
        super(message);
        responseStatus = ResponseStatus.SYSTEM_ERROR;
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
        responseStatus = ResponseStatus.SYSTEM_ERROR;
    }

    public GlobalException(Throwable cause) {
        super(cause);
        responseStatus = ResponseStatus.SYSTEM_ERROR;
    }

    public GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        responseStatus = ResponseStatus.SYSTEM_ERROR;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
