package com.ironman.forum.service;

import com.ironman.forum.dao.AboutMeDAO;
import com.ironman.forum.dao.CommonDAO;
import com.ironman.forum.dao.SearchLogDAO;
import com.ironman.forum.entity.AboutMe;
import com.ironman.forum.entity.SearchLog;
import com.ironman.forum.util.GlobalException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class AnsyCommonServiceImpl implements AnsyCommonService {

    @Autowired
    private SearchLogDAO searchLogDAO;

    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private AboutMeDAO aboutMeDAO;

    @Override
    @Async
    public void ansyChangeEntityPropertyNumById(String table, long id, String property, boolean isIncrement) {
        commonDAO.changePropertyNumById(table, id, property, isIncrement);
    }

    /**
     * 异步写入aboutme表
     */
    @Async
    @Override
    public void ansySaveAboutMe(AboutMe aboutMe) throws GlobalException {
        aboutMeDAO.save(aboutMe);
    }

    @Override
    public void ansyDeleteAboutMe(long id, int type) throws GlobalException {
        aboutMeDAO.deleteByLogIdAndType(id, type);
    }

    @Override
    public void ansyIncreasePropertyNumById(String table, long id, String property, int addNum) {
        commonDAO.increasePropertyNumById(table, id, property, addNum);
    }

    @Override
    public void ansyUpdateSearchLog(SearchLog searchLog) {
        searchLogDAO.update(searchLog);
    }
}
