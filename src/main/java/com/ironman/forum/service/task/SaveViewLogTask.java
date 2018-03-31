package com.ironman.forum.service.task;

import com.ironman.forum.dao.ViewLogDAO;
import com.ironman.forum.entity.ViewLog;
import com.ironman.forum.service.AnsyCommonService;
import com.ironman.forum.util.IronCache;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.IronUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Log4j
public class SaveViewLogTask {
    @Autowired
    private AnsyCommonService ansyCommonService;
    @Autowired
    private ViewLogDAO viewLogDAO;

    @Scheduled(cron = "0 0 0/2  * * ? ")
    public void saveViewLog() {
        log.info("后备viewLog定时任务启动");
        int size = IronCache.getViewLogCacheSize();
        if (size > 0 && size < IronConstant.VIEW_LOG_MAX_CACHE_SIZE) {
            log.info("处理残留缓存: " + size);
            List<ViewLog> viewLogList = IronCache.batchGetViewLogs(size);
            this.saveViewLogListToDB(viewLogList);
        }
        log.info("后备viewLog定时任务结束");
    }


    public void saveViewLogListToDB(List<ViewLog> viewLogList) {
        if (viewLogList != null && !viewLogList.isEmpty()) {
            log.info("执行批量log落库任务");
            try {
                Map<String, Integer> viewNumMap = IronUtil.constructViewNumMap(viewLogList);
                for (Map.Entry entry : viewNumMap.entrySet()) {
                    String key = entry.getKey().toString();
                    Long targetId = IronUtil.getTargetIdByViewNumMapKey(key);
                    int type = IronUtil.getTypeByViewNumMapKey(key);
                    int addNum = (int) entry.getValue();
                    //不是异步方法
                    ansyCommonService.increaseArticleViewLog(targetId, type, addNum);
                }
                viewLogDAO.batchSave(viewLogList);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            log.info("批量log落库任务完成");
        }
    }
}
