package com.ironman.forum.util;

import com.ironman.forum.entity.Moment;
import com.ironman.forum.vo.MomentVO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BeanUtils {
    public static <T> T copy(Object source, Class<T> destClass) throws GlobalException {
        Class sourceClass = source.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (sourceClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(sourceClass.getDeclaredFields()));
            sourceClass = sourceClass.getSuperclass(); //得到父类,然后赋给自己
        }
        try {
            T target = destClass.getConstructor().newInstance();
            for (Field field : fieldList) {
                field.setAccessible(true);
                try {
                    Field destField = destClass.getDeclaredField(field.getName());
                    destField.setAccessible(true);
                    destField.set(target, field.get(source));
                } catch (Exception e) {
                }
            }
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    public static void main(String[] args) throws GlobalException {
        Moment moment = new Moment();
        moment.setUserId(123);
        moment.setUniqueId("sss");
        moment.setContent("hahah");
        moment.setCreateTime(new Date());

        MomentVO momentVO = copy(moment, MomentVO.class);
        System.out.println(IronUtil.toJson(momentVO));
    }
}
