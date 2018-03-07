package com.ironman.forum.service;

import com.ironman.forum.form.MomentPublishForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.MomentVO;

import java.util.List;

public interface MomentService {
    void PublishMoment(MomentPublishForm form) throws GlobalException;

    List<MomentVO> pageMyMoments(PageRequest pageRequest) throws GlobalException;
}
