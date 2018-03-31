package com.ironman.forum.util;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j
public class IronExecutor {
    private static ExecutorService executorService = Executors.newFixedThreadPool(IronConstant.DEFAULT_POOL_SIZE);

    public static void execute(Runnable runnable) {
        executorService.execute(runnable);
    }

    public static void shutdown() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            log.info("ExecutorService shutdown successfully");
        }
    }
}
