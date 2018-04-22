package com.ironman.forum.util;

import com.ironman.forum.entity.Moment;
import com.ironman.forum.vo.MomentVO;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.Field;
import java.util.*;

@Log4j
public class BeanUtils {
    public static <T> T copy(Object source, Class<T> destClass) {
        Class sourceClass = source.getClass();
        Set<Field> sourceFieldSet = new HashSet<>();
        Map<String, Field> destFieldMap = new HashMap<>();

        while (sourceClass != null) {
            List<Field> fieldList = Arrays.asList(sourceClass.getDeclaredFields());
            sourceFieldSet.addAll(fieldList);
            sourceClass = sourceClass.getSuperclass();
        }
        sourceClass = destClass;

        while (sourceClass != null) {
            List<Field> fieldList = Arrays.asList(sourceClass.getDeclaredFields());
            for (Field field : fieldList) {
                destFieldMap.put(field.getName(), field);
            }
            sourceClass = sourceClass.getSuperclass();
        }

        T target = null;
        try {
            target = destClass.getConstructor().newInstance();
            for (Field field : sourceFieldSet) {
                Field destField = destFieldMap.get(field.getName());
                if (destField != null) {
                    if (field.getType().equals(destField.getType())) {
                        field.setAccessible(true);
                        destField.setAccessible(true);
                        destField.set(target, field.get(source));
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return target;
    }

    public static void main(String[] args) throws GlobalException {
        Moment moment = new Moment();
        moment.setId(222L);
        moment.setUserId(123);
        moment.setUniqueId("sss");
        moment.setContent("hahah");
        moment.setCreateTime(new Date());

        MomentVO momentVO = copy(moment, MomentVO.class);
        System.out.println(IronUtil.toJson(momentVO));
    }
}
