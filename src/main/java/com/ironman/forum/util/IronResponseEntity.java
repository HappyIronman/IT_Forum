package com.ironman.forum.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class IronResponseEntity extends ResponseEntity {

    public IronResponseEntity(ResponseStatus responseStatus, Object responseVO) {
        super(new ResponseBean(responseStatus, responseVO), responseStatus.getHttpStatus());
    }

    public IronResponseEntity(ResponseStatus responseStatus) {
        super(new ResponseBean(responseStatus), responseStatus.getHttpStatus());
    }

    public IronResponseEntity(ResponseStatus responseStatus, String msg) {
        super(new ResponseBean(responseStatus, msg), responseStatus.getHttpStatus());
    }

    public IronResponseEntity(HttpStatus statusCode) {
        super(statusCode);
    }

    public IronResponseEntity(Object body, HttpStatus statusCode) {
        super(body, statusCode);
    }

    public IronResponseEntity(MultiValueMap headers, HttpStatus statusCode) {
        super(headers, statusCode);
    }

    public IronResponseEntity(Object body, MultiValueMap headers, HttpStatus statusCode) {
        super(body, headers, statusCode);
    }
}
