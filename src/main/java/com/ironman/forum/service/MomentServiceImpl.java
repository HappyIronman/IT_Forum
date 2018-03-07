package com.ironman.forum.service;

import com.ironman.forum.dao.MomentDAO;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.dao.UserMomentDAO;
import com.ironman.forum.entity.Moment;
import com.ironman.forum.entity.User;
import com.ironman.forum.entity.UserMoment;
import com.ironman.forum.form.MomentPublishForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.util.Util;
import com.ironman.forum.vo.MomentVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j
public class MomentServiceImpl implements MomentService {

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private UserMomentDAO userMomentDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void PublishMoment(MomentPublishForm form) throws GlobalException {
        //校验逻辑...
        Long userId = 123L;
        Date date = new Date();
        Moment moment = new Moment();
        moment.setUserId(userId);
        moment.setUniqueId(Util.generateUniqueId());
        moment.setContent(form.getContent());
        moment.setDeleted(false);
        moment.setPrivate(form.getIsPrivate());
        moment.setCreateTime(date);

        momentDAO.save(moment);

        UserMoment userMoment = new UserMoment();
        userMoment.setUserId(userId);
        userMoment.setMomentId(moment.getId());
        userMoment.setPrivate(form.getIsPrivate());
        userMoment.setDeleted(false);
        userMoment.setType(UserMoment.Type.ORIGINAL.getType());
        userMoment.setCreateTime(date);

        userMomentDAO.save(userMoment);

        this.ansyChangeMomentNum(userId, true);
    }


    @Override
    public List<MomentVO> pageMyMoments(PageRequest pageRequest) throws GlobalException {
        Long userId = 123L;
        User user = userDAO.getMomentInfoById(userId);
        List<UserMoment> userMomentList = userMomentDAO.getAllLimitByUserId(userId, pageRequest);
        List<MomentVO> momentVOList = new ArrayList<>();
        if (userMomentList != null) {
            for (UserMoment userMoment : userMomentList) {
                Moment moment = momentDAO.getById(userMoment.getMomentId());
                MomentVO momentVO = new MomentVO();
                momentVO.setUsername(user.getUsername());
                momentVO.setProfile(user.getProfile());
                momentVO.setPrivate(userMoment.isPrivate());
                momentVO.setType(userMoment.getType());
                momentVO.setReason(userMoment.getReason());
                momentVO.setCreateTime(userMoment.getCreateTime());
                if (moment.getUserId() != userId && moment.isPrivate()) {
                    momentVO.setExist(false);
                } else {
                    momentVO.setExist(true);
                    momentVO.setUniqueId(moment.getUniqueId());
                    momentVO.setContent(moment.getContent());
                    momentVO.setLikeNum(moment.getLikeNum());
                    momentVO.setDislikeNum(moment.getDislikeNum());
                    momentVO.setCommentNum(moment.getCommentNum());
                    momentVO.setShareNum(moment.getShareNum());
                    momentVO.setViewNum(moment.getViewNum());
                }
                momentVOList.add(momentVO);
            }
        }
        return momentVOList;
    }

    /**
     * 异步更改用户发表moment的数量
     *
     * @param userId
     * @param isIncrement 是否为增加
     */
    @Async
    public void ansyChangeMomentNum(long userId, boolean isIncrement) {
        userDAO.changeMomentNum(userId, isIncrement);
    }
}
