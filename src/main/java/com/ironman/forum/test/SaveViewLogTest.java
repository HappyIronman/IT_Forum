package com.ironman.forum.test;

import com.ironman.forum.entity.EntityType;
import com.ironman.forum.entity.ViewLog;
import com.ironman.forum.service.AnsyCommonService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SaveViewLogTest extends BaseJunit4Test {

    @Autowired
    private AnsyCommonService ansyCommonService;

    @Test
    public void test() throws Exception {
        System.out.println("测试Spring整合Junit4进行单元测试");

        ViewLog viewLog = new ViewLog();
        viewLog.setUserId(33);
        viewLog.setLength(5);
        viewLog.setTargetId(1L);
        viewLog.setType(EntityType.MOMENT.getId());
        viewLog.setCreateTime(new Date());
        ansyCommonService.ansySaveViewLog(viewLog);
        System.out.println("viewlog1 异步保存");

        Thread.sleep(2000);
        viewLog = new ViewLog();
        viewLog.setUserId(34);
        viewLog.setLength(6);
        viewLog.setTargetId(1L);
        viewLog.setType(EntityType.BLOG.getId());
        viewLog.setCreateTime(new Date());
        ansyCommonService.ansySaveViewLog(viewLog);
        System.out.println("viewlog2 异步保存");
        Thread.sleep(2000);

        viewLog = new ViewLog();
        viewLog.setUserId(35);
        viewLog.setLength(7);
        viewLog.setTargetId(1L);
        viewLog.setType(EntityType.BLOG.getId());
        viewLog.setCreateTime(new Date());
        ansyCommonService.ansySaveViewLog(viewLog);
        System.out.println("viewlog3 异步保存");

        Thread.sleep(6000);
        System.out.println("退出");
    }
}
