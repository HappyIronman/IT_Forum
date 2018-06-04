package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.QuestionDAO;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.entity.ArticleTypeEnum;
import com.ironman.forum.entity.Question;
import com.ironman.forum.entity.User;
import com.ironman.forum.form.QuestionPublishForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.QuestionVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
@Log4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private AnsyService ansyService;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void publishQuestion(QuestionPublishForm form) {
        //1.校验 2.写question表 3.异步增加questionNum属性 4.异步写timeline表
        String title = form.getTitle();
        String content = form.getContent();
        Long userId = UserLoginUtil.getLoginUserId();
        Date createTime = new Date();

        Question question = new Question();
        question.setUniqueId(IronUtil.generateUniqueId());
        question.setUserId(userId);
        question.setTitle(title);
        question.setContent(content);
        question.setCreateTime(createTime);

        questionDAO.save(question);

        ansyService.ansyChangeUserPropertyNum(userId, IronConstant.USER_PROPERTY_QUESTION_NUM, true);

        ansyService.ansyAddTimeLine(userId, question.getId(), ArticleTypeEnum.QUESTION.getId());
    }

    @Override
    public QuestionVO getUserQuestionDetail(String uniqueId) throws GlobalException {
        Question question = questionDAO.getByUniqueId(uniqueId);
        if (question == null) {
            throw new GlobalException(ResponseStatus.QUESTION_NOT_EXIST);
        }

        return this.assembleQuestionDetailVO(question);
    }

    @Override
    public QuestionVO getMyQuestionDetail(String uniqueId) throws GlobalException {
        Question question = questionDAO.getByUniqueId(uniqueId);
        long userId = UserLoginUtil.getLoginUserId();
        if (question == null || question.getUserId() != userId) {
            throw new GlobalException(ResponseStatus.QUESTION_NOT_EXIST);
        }

        return this.assembleQuestionDetailVO(question);
    }

    private QuestionVO assembleQuestionDetailVO(Question question) {
        long userId = question.getUserId();
        User user = userDAO.getArticleBaseInfoById(userId);
        QuestionVO questionVO = BeanUtils.copy(question, QuestionVO.class);
        questionVO.setUserId(user.getUniqueId());
        questionVO.setUsername(user.getUsername());
        questionVO.setProfileUrl(commonService.concatImageUrl(user.getProfile()));
        return questionVO;
    }


    @Override
    public List<QuestionVO> pageMyQuestions(PageRequest pageRequest) {
        long userId = UserLoginUtil.getLoginUserId();
        List<Question> questionList = questionDAO.getAllLimitByUserId(userId, pageRequest);
        List<QuestionVO> questionVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(questionList)) {
            return questionVOList;
        }
        User user = userDAO.getArticleBaseInfoById(userId);

        for (Question question : questionList) {
            QuestionVO questionVO = this.assembleQuestionAbsVO(question, user);
            questionVOList.add(questionVO);
        }
        return questionVOList;
    }

    @Override
    public List<QuestionVO> pageUserQuestions(String userUniqueId, PageRequest pageRequest) {
        User user = userDAO.getArticleBaseInfoByUniqueId(userUniqueId);
        long userId = user.getId();
        List<Question> questionList = questionDAO.getAllLimitByUserId(userId, pageRequest);
        List<QuestionVO> questionVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(questionList)) {
            return questionVOList;
        }

        for (Question question : questionList) {
            QuestionVO questionVO = this.assembleQuestionAbsVO(question, user);
            questionVOList.add(questionVO);
        }
        return questionVOList;
    }

    @Override
    public QuestionVO assembleQuestionAbsVO(Question question) {
        long userId = question.getUserId();
        User user = userDAO.getArticleBaseInfoById(userId);
        return this.assembleQuestionAbsVO(question, user);
    }

    private QuestionVO assembleQuestionAbsVO(Question question, User user) {
        QuestionVO questionVO = BeanUtils.copy(question, QuestionVO.class);
        String content = IronUtil.removeHtmlTags(question.getContent());
        questionVO.setContent(content);
        questionVO.setUserId(user.getUniqueId());
        questionVO.setUsername(user.getUsername());
        questionVO.setProfileUrl(commonService.concatImageUrl(user.getProfile()));
        return questionVO;
    }
}
