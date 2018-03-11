package com.ironman.forum.service;

import com.ironman.forum.dao.CommonDAO;
import com.ironman.forum.dao.TimeLineDAO;
import com.ironman.forum.entity.TimeLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private TimeLineDAO timeLineDAO;
    @Autowired
    private CommonDAO commonDAO;

    @Override
    @Async
    public void ansyAddTimeLine(TimeLine timeLine) {
        timeLineDAO.save(timeLine);
    }

    /**
     * 异步更改数量
     */
    @Override
    @Async
    public void ansyChangeEntityPropertyNum(String table, long id, String property, boolean isIncrement) {
        commonDAO.changePropertyNum(table, id, property, isIncrement);
    }
}
