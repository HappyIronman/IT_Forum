package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.FollowDAO;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.EntityType;
import com.ironman.forum.entity.Follow;
import com.ironman.forum.entity.User;
import com.ironman.forum.form.UserLoginForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.FollowLog;
import com.ironman.forum.vo.FollowerVO;
import com.ironman.forum.vo.UserInfoVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private static Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private FollowDAO followDAO;

    @Autowired
    private AnsyCommonService ansyCommonService;

    @Override
    public UserInfoVO getMyBaseInfo() throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();
        User user = userDAO.getById(userId);
        if (user == null) {
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        UserInfoVO userInfoVO = BeanUtils.copy(user, UserInfoVO.class);
        return userInfoVO;
    }

    @Override
    public List<FollowerVO> pageMyFollowerList(PageRequest pageRequest) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();

        return this.getFollowerListByUserId(userId, pageRequest);
    }

    @Override
    public List<FollowerVO> pageMyFollowingList(PageRequest pageRequest) throws GlobalException {
        Long userId = UserLoginUtil.getLoginUserId();

        return this.getFollowingListByUserId(userId, pageRequest);
    }


    private List<FollowerVO> getFollowerListByUserId(Long userId, PageRequest pageRequest) throws GlobalException {
        List<Follow> followList = followDAO.getAllLimitByUserId(userId, pageRequest);
        List<FollowerVO> followerList = new ArrayList<>();
        if (followList == null || followList.size() == 0) {
            return followerList;
        }
        for (Follow follow : followList) {
            User user = userDAO.getById(follow.getFollowerId());
            if (user == null) {
                throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
            }
            FollowerVO followerVO = BeanUtils.copy(user, FollowerVO.class);
            followerVO.setFollowDate(follow.getCreateTime());

            //我是否是他的粉丝
            if (followDAO.getByFollowerIdAndUserId(userId, follow.getFollowerId()) == null) {
                followerVO.setRelation(UserInfoVO.Relation.HIS_IS_MY_FANS.getId());
            } else {
                followerVO.setRelation(UserInfoVO.Relation.FANS_TO_EACH_OTHER.getId());
            }

            followerList.add(followerVO);
        }
        return followerList;
    }


    private List<FollowerVO> getFollowingListByUserId(Long followerId, PageRequest pageRequest) throws GlobalException {
        List<Follow> followList = followDAO.getAllLimitByFollowerId(followerId, pageRequest);
        List<FollowerVO> followerList = new ArrayList<>();
        if (followList == null || followList.size() == 0) {
            return followerList;
        }
        for (Follow follow : followList) {
            User user = userDAO.getById(follow.getUserId());
            if (user == null) {
                throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
            }
            FollowerVO followerVO = BeanUtils.copy(user, FollowerVO.class);
            followerVO.setFollowDate(follow.getCreateTime());

            //他是否是我的粉丝
            if (followDAO.getByFollowerIdAndUserId(follow.getUserId(), followerId) == null) {
                followerVO.setRelation(UserInfoVO.Relation.I_AM_HIS_FANS.getId());
            } else {
                followerVO.setRelation(UserInfoVO.Relation.FANS_TO_EACH_OTHER.getId());
            }

            followerList.add(followerVO);
        }
        return followerList;
    }

    @Override
    public UserInfoVO getUserBaseInfo(String uniqueId) throws GlobalException {
        User user = userDAO.getByUniqueId(uniqueId);
        if (user == null) {
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        UserInfoVO userInfoVO = BeanUtils.copy(user, UserInfoVO.class);
        //获取我和他的关系
        Long selfId = UserLoginUtil.getLoginUserId();
        userInfoVO.setRelation(this.judgeUserRelation(selfId, user.getId()));
        return userInfoVO;
    }

    private int judgeUserRelation(long selfId, long userId) {
        //如果selfId和userId相等，直接返回互粉关系
        if (selfId == userId) {
            return UserInfoVO.Relation.FANS_TO_EACH_OTHER.getId();
        }

        //我是否是他的粉丝
        Follow selfFollow = followDAO.getByFollowerIdAndUserId(selfId, userId);
        //他是否是我的粉丝
        Follow userFollow = followDAO.getByFollowerIdAndUserId(userId, selfId);
        if (selfFollow == null) {
            if (userFollow == null) {
                return UserInfoVO.Relation.STRANGER.getId();
            } else {
                return UserInfoVO.Relation.HIS_IS_MY_FANS.getId();
            }
        } else {
            if (userFollow == null) {
                return UserInfoVO.Relation.I_AM_HIS_FANS.getId();
            } else {
                return UserInfoVO.Relation.FANS_TO_EACH_OTHER.getId();
            }
        }
    }

    @Override
    public void userLogin(UserLoginForm form, HttpSession session) throws GlobalException {
        User user = userDAO.getByUsernameAndPassword(form.getUsername(), form.getPassword());
        if (user == null) {
            throw new GlobalException(ResponseStatus.USERNAME_OR_PASSWORD_INCORRECT);
        }
        session.setAttribute(IronConstant.SESSION_USER_KEY, user);
        session.setAttribute(IronConstant.SESSION_ROLE_KEY, Arrays.asList("ROLE_USER"));
        log.info(IronUtil.toJson(session));
    }

    @Override
    @Transactional
    public int followUser(String userUniqueId) throws GlobalException {
        User user = userDAO.getByUniqueId(userUniqueId);
        if (user == null) {
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        long followerId = UserLoginUtil.getLoginUserId();
        long targetId = user.getId();

        Follow existFollow = followDAO.getByFollowerIdAndUserId(followerId, targetId);
        //不能重复关注
        if (existFollow != null) {
            throw new GlobalException(ResponseStatus.DUPLICATE_FOLLOW_LOG);
        }
        //插入follow表
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setUserId(targetId);
        follow.setDisabled(false);
        Date createTime = new Date();
        follow.setCreateTime(createTime);

        followDAO.save(follow);

        //异步增加被粉人粉丝数
        ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_USER, targetId,
                IronConstant.USER_PROPERTY_FOLLOWER_NUM, true);
        //异步增加粉丝关注数
        ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_USER, followerId,
                IronConstant.USER_PROPERTY_FOLLOWING_NUM, true);


        //异步插入aboutMe
        FollowLog followLog = new FollowLog();
        followLog.setId(follow.getId());
        followLog.setUserId(followerId);
        followLog.setTargetId(targetId);
        followLog.setDisabled(false);
        followLog.setType(EntityType.USER.getId());
        followLog.setCreateTime(createTime);
        ansyCommonService.ansySaveAboutMe(followLog);


        //他是否是我的粉丝
        if (followDAO.getByFollowerIdAndUserId(targetId, followerId) == null) {
            //他不是
            return UserInfoVO.Relation.I_AM_HIS_FANS.getId();
        } else {
            //他是，则关系为互粉
            return UserInfoVO.Relation.FANS_TO_EACH_OTHER.getId();
        }
    }
}
