package com.ironman.forum.dao;

import com.ironman.forum.entity.Share;
import org.apache.ibatis.annotations.Param;

public interface ShareDAO {
    Share getByArticleIdAndType(@Param("articleId") long articleId, @Param("type") int type);

    void save(Share share);
}
