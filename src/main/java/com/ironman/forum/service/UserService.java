package com.ironman.forum.service;

import com.ironman.forum.form.RegisterForm;
import com.ironman.forum.form.UserEditForm;
import com.ironman.forum.form.UserLoginForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.BaseLogVO;
import com.ironman.forum.vo.FollowerVO;
import com.ironman.forum.vo.UserInfoVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 */
public interface UserService {
    UserInfoVO getMyBaseInfo() throws GlobalException;

    List<FollowerVO> getFollowerListByUserId(Long userId, PageRequest pageRequest) throws GlobalException;

    UserInfoVO getUserBaseInfo(String uniqueId) throws GlobalException;

    int judgeUserRelation(long selfId, long userId);

    UserInfoVO userLogin(UserLoginForm form, HttpSession session) throws GlobalException;

    /**
     * @param userUniqueId
     * @return �������ע�˵Ĺ�ϵ, �μ�UserBaseVo.relationö����
     * @throws GlobalException
     */
    int followUser(String userUniqueId) throws GlobalException;

    /**
     * ��ȡ�ҵķ�˿�б�
     *
     * @param pageRequest
     * @return
     * @throws GlobalException
     */
    List<FollowerVO> pageMyFollowerList(PageRequest pageRequest) throws GlobalException;

    /**
     * ��ȡ�ҹ�ע�����б�
     *
     * @param pageRequest
     * @return
     * @throws GlobalException
     */
    List<FollowerVO> pageMyFollowingList(PageRequest pageRequest) throws GlobalException;

    List<BaseLogVO> pageAboutMeList(PageRequest pageRequest) throws GlobalException;

    void checkPhone(String phone) throws GlobalException;

    void checkUsername(String username) throws GlobalException;

    UserInfoVO register(RegisterForm form, HttpSession session) throws GlobalException;

    UserInfoVO editInfo(UserEditForm form, HttpSession session) throws GlobalException;

    void logout(HttpSession session);

    int getNewAboutMeNum();
}
