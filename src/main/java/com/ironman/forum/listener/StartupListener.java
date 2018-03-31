package com.ironman.forum.listener;

import com.ironman.forum.entity.ViewLog;
import com.ironman.forum.service.task.SaveViewLogTask;
import com.ironman.forum.util.IronCache;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.IronExecutor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Log4j
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SaveViewLogTask saveViewLogListToDB;

    /**
     * 可用spring定时任务代替
     *
     * @param contextRefreshedEvent the context refreshed event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("listener启动");
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    int cacheSize = IronCache.getViewLogCacheSize();
                                    if (cacheSize >= IronConstant.VIEW_LOG_MAX_CACHE_SIZE) {
                                        log.info("批量存储viewLog触发");
                                        IronExecutor.execute(batchSaveViewLogToDbTask());

                                        Thread.sleep(600);
                                    }
                                } catch (Exception e) {
                                    log.error(e.getMessage(), e);
                                }
                            }
                        }
                    }
            ).start();
        }
    }

    private Runnable batchSaveViewLogToDbTask() {
        return new Runnable() {
            @Override
            public void run() {
                List<ViewLog> viewLogList = IronCache.batchGetViewLogs(IronConstant.VIEW_LOG_MAX_CACHE_SIZE);
                saveViewLogListToDB.saveViewLogListToDB(viewLogList);
            }
        };
    }


}
