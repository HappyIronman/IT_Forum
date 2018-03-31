package com.ironman.forum.util;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.ironman.forum.entity.ViewLog;
import lombok.extern.log4j.Log4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static String concatImageUrl(String host, String imgName) throws GlobalException {
        String picUrl;
        if (StringUtils.isEmpty(host)) {
            throw new GlobalException(ResponseStatus.SYSTEM_ERROR, "hostÎª¿Õ");
        }
        if (host.endsWith("/")) {
            picUrl = host + "img/" + imgName;
        } else {
            picUrl = host + "/img/" + imgName;
        }
        return picUrl;
    }


    public static Map<String, Integer> constructViewNumMap(List<ViewLog> viewLogList) {
        Map<String, Integer> viewNumMap = new HashMap<>();
        for (ViewLog viewLog : viewLogList) {
            long targetId = viewLog.getTargetId();
            int type = viewLog.getType();
            String entityKey = targetId + ":" + type;
            if (viewNumMap.containsKey(entityKey)) {
                viewNumMap.put(entityKey, viewNumMap.get(entityKey) + 1);
            } else {
                viewNumMap.put(entityKey, 1);
            }
        }
        return viewNumMap;
    }

    public static long getTargetIdByViewNumMapKey(String key) throws GlobalException {
        String[] keyArray = key.split(":");
        if (keyArray.length != 2) {
            throw new GlobalException("ViewNumMap¼üÖµ½âÎö´íÎó: " + key);
        }
        return Long.parseLong(keyArray[0]);
    }

    public static int getTypeByViewNumMapKey(String key) throws GlobalException {
        String[] keyArray = key.split(":");
        if (keyArray.length != 2) {
            throw new GlobalException("ViewNumMap¼üÖµ½âÎö´íÎó: " + key);
        }
        return Integer.parseInt(keyArray[1]);
    }
}
