package com.ironman.forum.service;

import com.ironman.forum.entity.*;
import com.ironman.forum.util.GlobalException;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 异步dao动作封装类
 */
public interface AnsyService {

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

    @Async
    void ansyIncreasePropertyNumById(String table, long id, String property, int addNum);

    @Async
    void ansyUpdateSearchLog(SearchLog searchLog);

    @Async
    void ansySaveAboutMe(BaseLog baseLog) throws GlobalException;

    @Async
    void ansyDeleteAboutMe(BaseLog baseLog) throws GlobalException;

    @Async
    void ansyIncreaseArticleViewLog(long targetId, int type, int addNum) throws GlobalException;

    @Async
    void ansySaveViewLog(ViewLog viewLog) throws GlobalException;

    @Async
    void ansySaveViewLogList(List<ViewLog> viewLogList) throws GlobalException;

    @Async
    void ansyIncreaseCommentNum(Comment comment) throws GlobalException;

    @Async
    void ansyChangeArticlePropertyNum(int type, long targetId, String property, boolean isIncrement);

    @Async
    void ansyChangeUserPropertyNum(long userId, String property, boolean isIncrement);

    @Async
    void ansyAddTimeLine(Long userId, long articleId, int type);

    @Async
    void ansyUpdateAboutMeStatus(List<AboutMe> aboutMeList);
}
