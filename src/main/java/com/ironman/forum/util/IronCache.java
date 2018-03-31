package com.ironman.forum.util;

import com.ironman.forum.entity.ViewLog;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

@Log4j
public class IronCache {

    private static LinkedBlockingDeque<ViewLog> viewLogLinkedBlockingDeque = new LinkedBlockingDeque<>();

    public static void addViewLog(ViewLog viewLog) {
        try {
            viewLogLinkedBlockingDeque.put(viewLog);
            log.info("a new view log added");
        } catch (InterruptedException e) {
            //不影响执行
            log.error(e.getMessage(), e);
        }
    }

    public static ViewLog getViewLog() {
        try {
            return viewLogLinkedBlockingDeque.take();
        } catch (InterruptedException e) {
            //不影响执行
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static int getViewLogCacheSize() {
        return viewLogLinkedBlockingDeque.size();
    }


    public static List<ViewLog> batchGetViewLogs(int size) {
        if (size <= 0) {
            log.error("size不合法: " + size);
            return null;
        }
        List<ViewLog> viewLogList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (isViewLogCacheEmpty()) {
                break;
            }
            ViewLog viewLog = getViewLog();
            if (viewLog != null) {
                viewLogList.add(viewLog);
            }
        }
        return viewLogList;
    }

    public static boolean isViewLogCacheEmpty() {
        return viewLogLinkedBlockingDeque.isEmpty();
    }
}
