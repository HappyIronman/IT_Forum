package com.ironman.forum.util;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.UUID;

@Log4j
public class Util {
    public static String toJson(Object src) {
        Gson gson = new Gson();
        if (src == null) {
            return gson.toJson(JsonNull.INSTANCE);
        }
        return gson.toJson(src);
    }

    public static IronResponseEntity processResult(BindingResult result) {
        List<ObjectError> errors = result.getAllErrors();
        for (ObjectError error : errors) {
            log.error(error.getDefaultMessage());
        }
        return new IronResponseEntity(ResponseStatus.PARAM_ERROR, errors.get(0).getDefaultMessage());
    }

    public static String generateUniqueId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
