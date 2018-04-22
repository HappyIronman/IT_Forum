package com.ironman.forum.service;

import com.ironman.forum.entity.Article;
import com.ironman.forum.entity.BaseLog;
import com.ironman.forum.entity.Comment;
import com.ironman.forum.entity.ViewLog;
import com.ironman.forum.form.LikeArticleFormBean;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.vo.ImageVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author tracy
 * @date 2018-3-10 22:21:24
 */
public interface CommonService {



    void likeArticle(LikeArticleFormBean formBean) throws GlobalException;

    void cancelLikeArticle(LikeArticleFormBean formBean) throws GlobalException;

    void ansySaveAboutMe(BaseLog baseLog) throws GlobalException;

    /**
     * 根据uniqueId和type获取article基本信息
     *
     * @param uniqueId
     * @param type
     * @return
     */
    Article getArticleBaseInfoByUniqueIdAndType(String uniqueId, int type) throws GlobalException;

    List<ImageVO> saveImages(MultipartFile[] images) throws GlobalException;

    Article getArticleBaseInfoByIdAndType(long id, int type) throws GlobalException;

    int judgeLikeCondition(Article article) throws GlobalException;

    void ansyDeleteAboutMe(BaseLog baseLog) throws GlobalException;

    void ansyIncreaseArticleViewLog(long targetId, int type, int addNum) throws GlobalException;

    void ansySaveViewLog(ViewLog viewLog) throws GlobalException;

    void ansySaveViewLogList(List<ViewLog> viewLogList) throws GlobalException;

    void ansyIncreaseCommentNum(Comment comment) throws GlobalException;

    void ansyChangeArticlePropertyNum(int type, long targetId, String property, boolean isIncrement);

    @Async
    void ansyAddTimeLine(Long userId, long articleId, int type);
}
