package com.ironman.forum.service;

import com.ironman.forum.util.GlobalException;
import com.ironman.forum.vo.LikeArticleFormBean;

/**
 * ���з������ڴ˴�
 * @author tracy
 * @date 2018-3-10 22:21:24
 */
public interface CommonService {


    /**
     * �����µ��޻��
     *
     * @param formBean
     * @throws GlobalException
     */
    void likeArticle(LikeArticleFormBean formBean) throws GlobalException;

    /**
     * ������ȡ���޻��
     *
     * @param formBean
     * @throws GlobalException
     */
    void cancelLikeArticle(LikeArticleFormBean formBean) throws GlobalException;

    /**
     * ����uniqueId��type��ȡid
     *
     * @param uniqueId
     * @param type
     * @return
     */
    Long getArticleIdByUniqueIdAndType(String uniqueId, int type) throws GlobalException;

}
