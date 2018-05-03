package com.ironman.forum.service;

import com.ironman.forum.entity.Question;
import com.ironman.forum.form.QuestionPublishForm;
import com.ironman.forum.util.GlobalException;
import com.ironman.forum.util.PageRequest;
import com.ironman.forum.vo.QuestionVO;

import java.util.List;

public interface QuestionService {
    void publishQuestion(QuestionPublishForm form);

    QuestionVO getQuestionDetail(String uniqueId) throws GlobalException;

    List<QuestionVO> pageMyQuestions(PageRequest pageRequest);

    List<QuestionVO> pageUserQuestions(String userId, PageRequest pageRequest);

    QuestionVO assembleQuestionVO(Question question);
}
