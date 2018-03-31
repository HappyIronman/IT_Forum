package com.ironman.forum.service;

import com.ironman.forum.entity.BaseLog;
import com.ironman.forum.entity.TimeLine;
import com.ironman.forum.entity.ViewLog;
import com.ironman.forum.util.GlobalException;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

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

    @Async
    void ansySaveViewLog(ViewLog viewLog) throws GlobalException;

    void ansySaveViewLogList(List<ViewLog> viewLogList) throws GlobalException;

    void increaseArticleViewLog(long targetId, int type, int addNum) throws GlobalException;
}
