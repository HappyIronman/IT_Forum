package com.ironman.forum.service;

import com.ironman.forum.util.GlobalException;
import com.ironman.forum.vo.LikeArticleFormBean;

/**
 * 公有方法放在此处
 * @author tracy
 * @date 2018-3-10 22:21:24
 */
public interface CommonService {


    /**
     * 对文章点赞或踩
     *
     * @param formBean
     * @throws GlobalException
     */
    void likeArticle(LikeArticleFormBean formBean) throws GlobalException;

    /**
     * 对文章取消赞或踩
     *
     * @param formBean
     * @throws GlobalException
     */
    void cancelLikeArticle(LikeArticleFormBean formBean) throws GlobalException;

    /**
     * 根据uniqueId和type获取id
     *
     * @param uniqueId
     * @param type
     * @return
     */
    Long getArticleIdByUniqueIdAndType(String uniqueId, int type) throws GlobalException;

}
