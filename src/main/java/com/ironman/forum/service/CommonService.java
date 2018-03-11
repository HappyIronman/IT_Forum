package com.ironman.forum.service;

import com.ironman.forum.entity.TimeLine;
import org.springframework.scheduling.annotation.Async;

/**
 * @author tracy
 * @date 2018-3-10 22:21:24
 */
public interface CommonService {
    /**
     * Ansy add time line.
     *
     * @param timeLine the time line
     */
    @Async
    void ansyAddTimeLine(TimeLine timeLine);

    @Async
    void ansyChangeEntityPropertyNum(String table, long id, String property, boolean isIncrement);
}
