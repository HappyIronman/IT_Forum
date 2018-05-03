package com.ironman.forum.dao;

import com.ironman.forum.entity.Question;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionDAO {
    void save(Question question);

    Question getByUniqueId(String uniqueId);

    Question getById(long id);

    Question getBaseInfoByUniqueId(String uniqueId);

    Question getBaseInfoById(long id);

    List<Question> getAllLimitByUserId(@Param("userId") Long userId, @Param("pageRequest") PageRequest pageRequest);

}
