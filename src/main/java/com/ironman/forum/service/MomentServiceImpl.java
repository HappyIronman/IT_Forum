package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.ImageDAO;
import com.ironman.forum.dao.MomentDAO;
import com.ironman.forum.dao.ShareDAO;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.*;
import com.ironman.forum.form.MomentPublishForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.MomentVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j
public class MomentServiceImpl implements MomentService {

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private ShareDAO shareDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ImageDAO imageDAO;

    @Autowired
    private CommonService commonService;

    @Override
    @Transactional
    public void publishMoment(MomentPublishForm form) throws GlobalException {
        log.info(IronUtil.toJson(form));
        //校验逻辑...
        Long userId = UserLoginUtil.getLoginUserId();
        Date date = new Date();
        Moment moment = new Moment();
        moment.setUserId(userId);
        moment.setUniqueId(IronUtil.generateUniqueId());
        moment.setContent(form.getContent());
        moment.setDeleted(false);
        moment.setPrivate(form.getIsPrivate());
        moment.setShare(form.getIsShare());
        moment.setContainPic(form.getIsContainPic());
        moment.setCreateTime(date);

        momentDAO.save(moment);

        if (form.getIsContainPic()) {
            List<String> picNameList = form.getPicNameList();
            if (picNameList != null && picNameList.size() != 0) {
                for (String name : picNameList) {
                    Image image = new Image();
                    image.setName(name);
                    image.setUserId(userId);
                    image.setArticleId(moment.getId());
                    image.setType(ArticleTypeEnum.MOMENT.getId());
                    image.setDeleted(false);
                    image.setCreateTime(new Date());
                    imageDAO.save(image);
                }
            }
        }

        if (form.getIsShare()) {
            String originUniqueId = form.getOriginId();
            if (StringUtils.isEmpty(originUniqueId)) {
                throw new GlobalException(ResponseStatus.PARAM_ERROR, "原文章UniqueId不能为空");
            }
            Moment originMoment = momentDAO.getBaseInfoByUniqueId(originUniqueId);
            if (originMoment == null || originMoment.isPrivate()) {
                throw new GlobalException(ResponseStatus.MOMENT_NOT_EXIST);
            }
            Share share = new Share();
            share.setArticleId(moment.getId());
            share.setOriginId(originMoment.getId());
            share.setType(ArticleTypeEnum.MOMENT.getId());
            share.setDeleted(false);
            share.setCreateTime(date);
            shareDAO.save(share);

            commonService.ansyChangeArticlePropertyNum(ArticleTypeEnum.MOMENT.getId(),
                    originMoment.getId(), IronConstant.ARTICLE_PROPERTY_SHARE_NUM, true);
        }

        commonService.ansyChangeUserPropertyNum(userId, IronConstant.USER_PROPERTY_MOMENT_NUM, true);

        //如果不是私人权限，异步插入时间轴
        if (!form.getIsPrivate()) {
            commonService.ansyAddTimeLine(userId, moment.getId(), ArticleTypeEnum.MOMENT.getId());
        }
    }


    @Override
    public List<MomentVO> pageMyMoments(PageRequest pageRequest) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        User user = userDAO.getArticleBaseInfoById(userId);
        List<Moment> momentList = momentDAO.getAllLimitByUserId(userId, pageRequest);
        List<MomentVO> momentVOList = new ArrayList<>();
        if (momentList != null) {
            for (Moment moment : momentList) {
                MomentVO momentVO = this.assembleMomentVO(moment, user);
                momentVOList.add(momentVO);
            }
        }

        //执行代理异步添加访问日志
        return momentVOList;
    }

    @Override
    public List<MomentVO> pageUserMoments(String userUniqueId, PageRequest pageRequest) throws GlobalException {
        User user = userDAO.getArticleBaseInfoByUniqueId(userUniqueId);
        if (user == null) {
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        List<Moment> momentList = momentDAO.getPublicLimitByUserId(user.getId(), pageRequest);
        List<MomentVO> momentVOList = new ArrayList<>();
        if (momentList != null) {
            for (Moment moment : momentList) {
                MomentVO momentVO = this.assembleMomentVO(moment, user);
                momentVOList.add(momentVO);
            }
        }
        //执行代理异步添加访问日志
        return momentVOList;
    }

    @Override
    public MomentVO assembleMomentVO(Moment moment) throws GlobalException {
        User user = userDAO.getArticleBaseInfoById(moment.getUserId());
        return this.assembleMomentVO(moment, user);
    }

    @Override
    public MomentVO assembleMomentVO(Moment moment, User user) throws GlobalException {
        MomentVO momentVO = BeanUtils.copy(moment, MomentVO.class);
        momentVO.setUserId(user.getUniqueId());
        momentVO.setUsername(user.getUsername());
        momentVO.setProfileUrl(commonService.concatImageUrl(user.getProfile()));

        momentVO.setLikeCondition(commonService.judgeLikeCondition(moment));

        if (moment.isShare()) {
            this.assembleMomentShareInfo(momentVO, moment);
        }
        if (moment.isContainPic()) {
            this.assembleMomentPicInfo(momentVO, moment);
        }
        return momentVO;
    }

    private void assembleMomentPicInfo(MomentVO momentVO, Moment moment) throws GlobalException {
        List<Image> imageList = imageDAO.getAllByArticleIdAndType(moment.getId(), ArticleTypeEnum.MOMENT.getId());
        if (imageList != null && imageList.size() != 0) {
            List<String> picUrlList = new ArrayList<>(imageList.size());
            for (Image image : imageList) {
                String picUrl = commonService.concatImageUrl(image.getName());
                picUrlList.add(picUrl);
            }
            momentVO.setPicUrlList(picUrlList);
        }
    }

    private void assembleMomentShareInfo(MomentVO momentVO, Moment moment) throws GlobalException {
        Share share = shareDAO.getByArticleIdAndType(moment.getId(), ArticleTypeEnum.MOMENT.getId());
        if (share == null) {
            log.error(moment.getId() + " 分享信息为空");
            throw new GlobalException(ResponseStatus.SYSTEM_ERROR);
        }
        Moment originMoment = momentDAO.getById(share.getOriginId());
        if (originMoment.isPrivate()) {
            momentVO.setExist(false);
        } else {
            momentVO.setExist(true);
            User originUser = userDAO.getArticleBaseInfoById(originMoment.getUserId());
            momentVO.setOriginUserId(originUser.getUniqueId());
            momentVO.setOriginUsername(originUser.getUsername());
            momentVO.setOriginUserId(originUser.getUniqueId());
            momentVO.setOriginContent(
                    IronUtil.getAbstractContent(originMoment.getContent(), IronConstant.MOMENT_MAX_LENGTH)
                            .getContent());
            momentVO.setOriginCreateTime(originMoment.getCreateTime());
        }
    }
}
