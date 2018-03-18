package com.ironman.forum.service;

import com.ironman.forum.entity.BaseLog;
import com.ironman.forum.entity.TimeLine;
import com.ironman.forum.util.GlobalException;
import org.springframework.scheduling.annotation.Async;

/**
 * �첽�¼��������
 */
public interface AnsyCommonService {
    /**
     * �첽����ʱ�����¼�
     *
     * @param timeLine the time line
     */
    @Async
    void ansyAddTimeLine(TimeLine timeLine);

    /**
     * �첽���ӻ���ٱ���ĳһ���ֵ
     *
     * @param table
     * @param id
     * @param property
     * @param isIncrement
     */
    @Async
    void ansyChangeEntityPropertyNumById(String table, long id, String property, boolean isIncrement);

    /**
     * �첽��������ҵļ�¼
     *
     * @param baseLog
     * @throws GlobalException
     */
    @Async
    void ansySaveAboutMe(BaseLog baseLog) throws GlobalException;

    /**
     * �첽ɾ�������ҵļ�¼
     *
     * @param baseLog
     * @throws GlobalException
     */
    @Async
    void ansyDeleteAboutMe(BaseLog baseLog) throws GlobalException;
}
