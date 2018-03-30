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
}
