package com.ironman.forum.service;

import com.ironman.forum.entity.Article;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author tracy
 * @date 2018-3-10 22:21:24
 */
public interface CommonService {

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

    Article getArticleDetailInfoByIdAndType(long id, int type) throws GlobalException;

    int judgeLikeCondition(Article article) throws GlobalException;

    String concatImageUrl(String imgName);
}
