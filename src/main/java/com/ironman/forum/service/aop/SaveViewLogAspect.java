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
        log.info("执行方法getBlogDetail返回后运行，参数为:" + blogDetailVO);
        ViewLog viewLog = new ViewLog();
        long userId = UserLoginUtil.getLoginUserId();
        viewLog.setUserId(userId);
        viewLog.setType(EntityTypeEnum.BLOG.getId());
        viewLog.setTargetId(blogDetailVO.getId());
        viewLog.setDisabled(false);
        viewLog.setCreateTime(new Date());
        try {
            //注意，此处必须同步插入db，因为后面要用到id，所以不能加入缓存
            ansyCommonService.increaseArticleViewLog(viewLog.getTargetId(), viewLog.getType(), 1);
            viewLogDAO.save(viewLog);
            //匿名用户访问不写aboutMe表
            if (userId == IronConstant.ANONYMOUS_USER_ID) {
                return;
            }
            //如果不是自己看自己的文章，需要写入aboutme表
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
        System.out.println("执行方法returningPageMoments返回后运行，参数为:" + momentVOList);

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
        System.out.println("执行方法pageMyFriendCircle返回后运行，参数为:" + timeLineVOList);

        List<ViewLog> viewLogList = new ArrayList<>();
        for (TimeLineVO timeLineVO : timeLineVOList) {
            int type = timeLineVO.getType();
            //此处blog不需要记录viewLog
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
