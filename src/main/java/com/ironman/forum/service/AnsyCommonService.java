package com.ironman.forum.service;

import com.ironman.forum.entity.BaseLog;
import com.ironman.forum.entity.TimeLine;
import com.ironman.forum.util.GlobalException;
import org.springframework.scheduling.annotation.Async;

/**
 * 异步事件处理服务
 */
public interface AnsyCommonService {
    /**
     * 异步增加时间轴事件
     *
     * @param timeLine the time line
     */
    @Async
    void ansyAddTimeLine(TimeLine timeLine);

    /**
     * 异步增加或减少表中某一项的值
     *
     * @param table
     * @param id
     * @param property
     * @param isIncrement
     */
    @Async
    void ansyChangeEntityPropertyNumById(String table, long id, String property, boolean isIncrement);

    /**
     * 异步保存关于我的记录
     *
     * @param baseLog
     * @throws GlobalException
     */
    @Async
    void ansySaveAboutMe(BaseLog baseLog) throws GlobalException;

    /**
     * 异步删除关于我的记录
     *
     * @param baseLog
     * @throws GlobalException
     */
    @Async
    void ansyDeleteAboutMe(BaseLog baseLog) throws GlobalException;
}
