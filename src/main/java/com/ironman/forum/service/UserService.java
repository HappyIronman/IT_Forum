package com.ironman.forum.service;

import com.ironman.forum.form.UserLoginForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.FollowerVO;
import com.ironman.forum.vo.UserInfoVO;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    UserInfoVO getMyBaseInfo() throws GlobalException;

    UserInfoVO getUserBaseInfo(String uniqueId) throws GlobalException;

    void userLogin(UserLoginForm form, HttpSession session) throws GlobalException;

    /**
     * @param userUniqueId
     * @return 本人与关注人的关系, 参见UserBaseVo.relation枚举类
     * @throws GlobalException
     */
    int followUser(String userUniqueId) throws GlobalException;

    /**
     * 获取我的粉丝列表
     *
     * @param pageRequest
     * @return
     * @throws GlobalException
     */
    List<FollowerVO> pageMyFollowerList(PageRequest pageRequest) throws GlobalException;

    /**
     * 获取我关注的人列表
     *
     * @param pageRequest
     * @return
     * @throws GlobalException
     */
    List<FollowerVO> pageMyFollowingList(PageRequest pageRequest) throws GlobalException;
}
