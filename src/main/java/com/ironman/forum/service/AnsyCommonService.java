package com.ironman.forum.service;

import com.ironman.forum.entity.AboutMe;
import com.ironman.forum.util.GlobalException;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步dao动作封装类
 */
public interface AnsyCommonService {

    /**
     * 异步更改属性数量
     *
     * @param table
     * @param id
     * @param property
     * @param isIncrement
     */
    @Async
    void ansyChangeEntityPropertyNumById(String table, long id, String property, boolean isIncrement);


    /**
     * 异步保存aboutme
     *
     * @param aboutMe
     * @throws GlobalException
     */
    @Async
    void ansySaveAboutMe(AboutMe aboutMe) throws GlobalException;

    /**
     * 异步删除aboutMe
     */
    @Async
    void ansyDeleteAboutMe(long id, int type) throws GlobalException;

    @Async
    void ansyIncreasePropertyNumById(String table, long id, String property, int addNum);
}
