package com.ironman.forum.dao;

import com.ironman.forum.entity.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImageDAO {
    void save(Image image);

    List<Image> getAllByArticleIdAndType(@Param("articleId") long articleId, @Param("type") int type);
}
