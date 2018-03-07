package com.ironman.forum.dao;

import com.ironman.forum.entity.Moment;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentDAO {
    void save(Moment moment);

    Moment getById(Long id);
}
