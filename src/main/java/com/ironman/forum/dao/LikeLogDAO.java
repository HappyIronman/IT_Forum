package com.ironman.forum.dao;

import com.ironman.forum.entity.LikeLog;
import org.apache.ibatis.annotations.Param;

public interface LikeLogDAO {
    void save(LikeLog likeLog);

    /**
     * Ω´disabled÷√Œªtrue
     *
     * @param userId
     * @param targetId
     * @param type
     * @param isLike
     * @return
     */
    int updateDisabledByUserIdAndTargetIdAndTypeAndIsLike(@Param("userId") Long userId, @Param("targetId") long targetId,
                                                          @Param("type") int type, @Param("isLike") boolean isLike);

    LikeLog getByUserIdAndTargetIdAndType(@Param("userId") Long userId, @Param("targetId") long targetId,
                                          @Param("type") int type);

    LikeLog getById(long id);
}
