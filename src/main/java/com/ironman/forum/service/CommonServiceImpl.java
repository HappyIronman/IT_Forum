package com.ironman.forum.service;

import com.ironman.forum.dao.BlogDAO;
import com.ironman.forum.dao.LikeLogDAO;
import com.ironman.forum.dao.MomentDAO;
import com.ironman.forum.entity.ArticleType;
import com.ironman.forum.entity.Blog;
import com.ironman.forum.entity.LikeLog;
import com.ironman.forum.entity.Moment;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.IronConstant;
import com.ironman.forum.util.ResponseStatus;
import com.ironman.forum.vo.LikeArticleFormBean;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Log4j
public class CommonServiceImpl implements CommonService {


    @Autowired
    private LikeLogDAO likeLogDAO;

    @Autowired
    private AnsyCommonService ansyCommonService;

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private BlogDAO blogDAO;


    @Override
    public void likeArticle(LikeArticleFormBean formBean) throws GlobalException {
        Long userId = 123L;
        int type = formBean.getType();
        Long targetId = this.getArticleIdByUniqueIdAndType(formBean.getTargetId(), type);
        boolean isLike = formBean.isLike();
        //�ӹ���������������
        LikeLog existLikeLog = likeLogDAO.getByUserIdAndTargetIdAndType(userId, targetId, type);
        if (existLikeLog != null) {
            //�����ظ��޻��߲�
            throw new GlobalException(ResponseStatus.DUPLICATE_LIKE_LOG);
        }

        //�����޻�ȼ�¼
        LikeLog likeLog = new LikeLog();
        likeLog.setUserId(userId);
        likeLog.setTargetId(targetId);
        likeLog.setType(type);
        likeLog.setLike(isLike);
        likeLog.setDisabled(false);
        likeLog.setCreateTime(new Date());

        likeLogDAO.save(likeLog);

        //�첽���Ӷ�Ӧ�����޻������
        this.changeArticleLikeNum(likeLog, true);

        //�첽д��aboutMe��
        ansyCommonService.ansySaveAboutMe(likeLog);
    }

    @Override
    @Transactional
    public void cancelLikeArticle(LikeArticleFormBean formBean) throws GlobalException {
        Long userId = 123L;
        int type = formBean.getType();
        Long targetId = this.getArticleIdByUniqueIdAndType(formBean.getTargetId(), type);
        boolean isLike = formBean.isLike();
        LikeLog likeLog = likeLogDAO.getByUserIdAndTargetIdAndType(userId, targetId, type);
        if (null == likeLog) {
            //֤��������ƥ���¼������
            throw new GlobalException(ResponseStatus.LIKE_LOG_NOT_EXIST);
        }
        likeLogDAO.updateDisabledByUserIdAndTargetIdAndTypeAndIsLike(userId, targetId, type, isLike);
        //�첽���ٶ�Ӧ�����޻������
        this.changeArticleLikeNum(likeLog, false);
        //�첽ɾ��aboutMe���¼
        ansyCommonService.ansyDeleteAboutMe(likeLog);
    }


    @Override
    public Long getArticleIdByUniqueIdAndType(String uniqueId, int type) throws GlobalException {
        Long id;
        if (type == ArticleType.COMMENT.getId()) {
            //todo
            id = null;
        } else if (type == ArticleType.MOMENT.getId()) {
            Moment moment = momentDAO.getBaseInfoByUniqueId(uniqueId);
            if (moment == null) {
                throw new GlobalException(ResponseStatus.MOMENT_NOT_EXIST);
            }
            id = moment.getId();
        } else if (type == ArticleType.BLOG.getId()) {
            Blog blog = blogDAO.getBaseInfoByUniqueId(uniqueId);
            if (blog == null) {
                throw new GlobalException(ResponseStatus.BLOG_NOT_EXIST);
            }
            id = blog.getId();
        } else if (type == ArticleType.QUESTION.getId()) {
            //todo
            id = null;
        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }
        return id;
    }

    /**
     * �첽�ı��Ӧ�����޻������
     *
     * @param likeLog
     */
    private void changeArticleLikeNum(LikeLog likeLog, boolean isIncrement) {
        int type = likeLog.getType();
        long targetId = likeLog.getTargetId();
        String property = likeLog.isLike() ? IronConstant.ARTICLE_PROPERTY_LIKE_NUM : IronConstant.ARTICLE_PROPERTY_DISLIKE_NUM;
        if (type == ArticleType.COMMENT.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_COMMENT, targetId, property, isIncrement);
        } else if (type == ArticleType.MOMENT.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_MOMENT, targetId, property, isIncrement);
        } else if (type == ArticleType.BLOG.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_BLOG, targetId, property, isIncrement);
        } else if (type == ArticleType.QUESTION.getId()) {
            ansyCommonService.ansyChangeEntityPropertyNumById(IronConstant.TABLE_QUESTION, targetId, property, isIncrement);
        }
    }


}
