package com.ironman.forum.test;

import com.ironman.forum.entity.ArticleTypeEnum;
import com.ironman.forum.entity.ViewLog;
import com.ironman.forum.service.AnsyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SaveViewLogTest extends BaseJunit4Test {

    @Autowired
    private AnsyService ansyService;

    @Test
    public void test() throws Exception {
        System.out.println("����Spring����Junit4���е�Ԫ����");

        ViewLog viewLog = new ViewLog();
        viewLog.setUserId(33);
        viewLog.setLength(5);
        viewLog.setTargetId(1L);
        viewLog.setType(ArticleTypeEnum.MOMENT.getId());
        viewLog.setCreateTime(new Date());
        ansyService.ansySaveViewLog(viewLog);
        System.out.println("viewlog1 �첽����");

        Thread.sleep(2000);
        viewLog = new ViewLog();
        viewLog.setUserId(34);
        viewLog.setLength(6);
        viewLog.setTargetId(1L);
        viewLog.setType(ArticleTypeEnum.BLOG.getId());
        viewLog.setCreateTime(new Date());
        ansyService.ansySaveViewLog(viewLog);
        System.out.println("viewlog2 �첽����");
        Thread.sleep(2000);

        viewLog = new ViewLog();
        viewLog.setUserId(35);
        viewLog.setLength(7);
        viewLog.setTargetId(1L);
        viewLog.setType(ArticleTypeEnum.BLOG.getId());
        viewLog.setCreateTime(new Date());
        ansyService.ansySaveViewLog(viewLog);
        System.out.println("viewlog3 �첽����");

        Thread.sleep(6000);
        System.out.println("�˳�");
    }
}
