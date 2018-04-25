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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (StringUtils.isEmpty(originContent)) {
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

    public static String getAbstractContentStr(String originContent, int targetLength) {
        AbstractContent abstractContent = new AbstractContent();
        if (StringUtils.isEmpty(originContent)) {
            return "";
        }
        if (originContent.length() > targetLength) {
            return originContent.substring(0, targetLength);
        } else {
            return originContent;
        }
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
            throw new GlobalException("ViewNumMap��ֵ��������: " + key);
        }
        return Long.parseLong(keyArray[0]);
    }

    public static int getTypeByViewNumMapKey(String key) throws GlobalException {
        String[] keyArray = key.split(":");
        if (keyArray.length != 2) {
            throw new GlobalException("ViewNumMap��ֵ��������: " + key);
        }
        return Integer.parseInt(keyArray[1]);
    }


    public static String removeHtmlTags(String htmlStr) {
        log.info(htmlStr);
        String reg = "<[^>]+>"; //����HTML��ǩ��������ʽ
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(htmlStr);
        String result = matcher.replaceAll(""); //����html��ǩ
        log.info("���˺���: " + result);
        return result;
    }

    /**
     * ֻ��ȡ��һ�γ��ֵĸ����ַ���������
     *
     * @param originContent
     * @return
     */
    public static String getSearchAbsContent(String originContent) {

        String destContent;
        int destLength = IronConstant.ES_BLOG_MAX_SIZE;
        if (StringUtils.isEmpty(originContent)) {
            return originContent;
        }
        if (originContent.length() <= destLength) {
            destContent = originContent;
        } else {
            int firstIndex = originContent.indexOf(IronConstant.ES_PRE_TAGS);
            if (firstIndex < 0) {
                log.info("û�и����ַ�,��ȡ����");
                destContent = originContent.substring(0, destLength);
            } else {
                int lastIndex = originContent.indexOf(IronConstant.ES_POST_TAGS);
                if (lastIndex < 0) {
                    log.error("����,û�и�����׺");
                    destContent = originContent.substring(0, destLength);
                } else {

                    lastIndex = lastIndex + IronConstant.ES_POST_TAGS.length();

                    int startPoint = (firstIndex - (destLength / 2)) < 0 ? 0 : (firstIndex - (destLength / 2));
                    int endPoint;
                    if (startPoint == 0) {
                        endPoint = destLength;
                    } else {
                        if ((lastIndex + (destLength / 2)) > originContent.length()) {
                            endPoint = originContent.length();
                            startPoint = startPoint - ((lastIndex + (destLength / 2)) - originContent.length());
                        } else {
                            endPoint = (lastIndex + (destLength / 2));
                        }
                    }

                    destContent = originContent.substring(startPoint, endPoint);
                }
            }
        }

        //�滻�Զ����ǩΪ������ǩ
        destContent = destContent.replaceAll(IronConstant.ES_PRE_TAGS, "<em>")
                .replaceAll(IronConstant.ES_POST_TAGS, "</em>");
        log.info("��ȡ������:" + destContent);
        return destContent;

    }
}
