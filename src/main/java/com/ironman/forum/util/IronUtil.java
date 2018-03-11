package com.ironman.forum.util;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.UUID;

@Log4j
public class IronUtil {
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

    public static AbstractContent getAbstractContent(String originContent, int targetLength) {
        AbstractContent abstractContent = new AbstractContent();
        if (StringUtils.isEmpty(abstractContent)) {
            return abstractContent;
        }
        if (originContent.length() > targetLength) {
            abstractContent.setAbstract(true);
            abstractContent.setContent(originContent.substring(0, targetLength));
        } else {
            abstractContent.setAbstract(false);
            abstractContent.setContent(originContent);
        }
        return abstractContent;
    }
}
