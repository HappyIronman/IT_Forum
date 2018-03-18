package com.ironman.forum.dao;

import com.ironman.forum.entity.Moment;
import com.ironman.forum.util.PageRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MomentDAO {
    void save(Moment moment);

    Moment getById(Long id);

    Moment getByUniqueId(String uniqueId);

    Moment getBaseInfoById(Long id);

    Moment getBaseInfoByUniqueId(String unqueId);

    List<Moment> getAllLimitByUserId(@Param("userId") Long userId, @Param("pageRequest") PageRequest pageRequest);
}
