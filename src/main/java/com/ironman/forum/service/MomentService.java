package com.ironman.forum.service;

import com.ironman.forum.entity.Moment;
import com.ironman.forum.entity.User;
import com.ironman.forum.form.MomentPublishForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.MomentVO;

import java.util.List;

public interface MomentService {
    void publishMoment(MomentPublishForm form) throws GlobalException;

    List<MomentVO> pageMyMoments(PageRequest pageRequest) throws GlobalException;

    MomentVO assembleMomentVO(Moment moment, User user) throws GlobalException;

    MomentVO assembleMomentVO(Moment moment) throws GlobalException;
}
