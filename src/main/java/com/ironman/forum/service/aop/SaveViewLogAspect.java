package com.ironman.forum.service.aop;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.ViewLogDAO;
import com.ironman.forum.entity.EntityTypeEnum;
import com.ironman.forum.entity.ViewLog;
import com.ironman.forum.service.AnsyCommonService;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.BlogDetailVO;
import com.ironman.forum.vo.MomentVO;
import com.ironman.forum.vo.TimeLineVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Log4j
public class SaveViewLogAspect {
    @Autowired
    private AnsyCommonService ansyCommonService;

    @Autowired
    private ViewLogDAO viewLogDAO;

    public void afterReturningGetBlogDetail(String uniqueId, BlogDetailVO blogDetailVO) {
        if (blogDetailVO == null) {
            return;
        }
        log.info("ִ�з���getBlogDetail���غ����У�����Ϊ:" + blogDetailVO);
        ViewLog viewLog = new ViewLog();
        long userId = UserLoginUtil.getLoginUserId();
        viewLog.setUserId(userId);
        viewLog.setType(EntityTypeEnum.BLOG.getId());
        viewLog.setTargetId(blogDetailVO.getId());
        viewLog.setDisabled(false);
        viewLog.setCreateTime(new Date());
        try {
            //ע�⣬�˴�����ͬ������db����Ϊ����Ҫ�õ�id�����Բ��ܼ��뻺��
            ansyCommonService.increaseArticleViewLog(viewLog.getTargetId(), viewLog.getType(), 1);
            viewLogDAO.save(viewLog);
            //�����û����ʲ�дaboutMe��
            if (userId == IronConstant.ANONYMOUS_USER_ID) {
                return;
            }
            //��������Լ����Լ������£���Ҫд��aboutme��
            if (!blogDetailVO.getUserId().equals(UserLoginUtil.getLoginUserUniqueId())) {
                ansyCommonService.ansySaveAboutMe(viewLog);
            }
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void afterReturningPageMoments(PageRequest pageRequest, List<MomentVO> momentVOList) {
        if (momentVOList == null || momentVOList.size() == 0) {
            return;
        }
        System.out.println("ִ�з���returningPageMoments���غ����У�����Ϊ:" + momentVOList);

        List<ViewLog> viewLogList = new ArrayList<>();
        for (MomentVO momentVO : momentVOList) {
            ViewLog viewLog = new ViewLog();
            viewLog.setUserId(UserLoginUtil.getLoginUserId());
            viewLog.setType(EntityTypeEnum.MOMENT.getId());
            viewLog.setTargetId(momentVO.getId());
            viewLog.setDisabled(false);
            viewLog.setCreateTime(new Date());
            viewLogList.add(viewLog);
        }
        try {
            ansyCommonService.ansySaveViewLogList(viewLogList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void afterReturningPageUserMoments(String userUniqueId, PageRequest pageRequest, List<MomentVO> momentVOList) {
        this.afterReturningPageMoments(pageRequest, momentVOList);
    }


    public void afterReturningPageMyFriendCircle(PageRequest pageRequest, List<TimeLineVO> timeLineVOList) {
        if (timeLineVOList == null || timeLineVOList.size() == 0) {
            return;
        }
        System.out.println("ִ�з���pageMyFriendCircle���غ����У�����Ϊ:" + timeLineVOList);

        List<ViewLog> viewLogList = new ArrayList<>();
        for (TimeLineVO timeLineVO : timeLineVOList) {
            int type = timeLineVO.getType();
            //�˴�blog����Ҫ��¼viewLog
            ViewLog viewLog = new ViewLog();
            if (type == EntityTypeEnum.MOMENT.getId()) {
                MomentVO momentVO = (MomentVO) timeLineVO.getEntity();
                viewLog.setUserId(UserLoginUtil.getLoginUserId());
                viewLog.setType(EntityTypeEnum.MOMENT.getId());
                viewLog.setTargetId(momentVO.getId());
                viewLog.setDisabled(false);
                viewLog.setCreateTime(new Date());
                viewLogList.add(viewLog);
            } else if (type == EntityTypeEnum.QUESTION.getId()) {
                //todo
            } else if (type == EntityTypeEnum.COMMENT.getId()) {
                //todo
            }
        }
        try {
            ansyCommonService.ansySaveViewLogList(viewLogList);
        } catch (GlobalException e) {
            log.error(e.getMessage(), e);
        }
    }
}
