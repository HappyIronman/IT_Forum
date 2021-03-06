package com.ironman.forum.service;

import com.ironman.forum.conf.UserLoginUtil;
import com.ironman.forum.dao.UserDAO;
import com.ironman.forum.dao.es.EsBlogRepository;
import com.ironman.forum.entity.ArticleTypeEnum;
import com.ironman.forum.entity.SearchLog;
import com.ironman.forum.entity.User;
import com.ironman.forum.entity.es.EsBlog;
import com.ironman.forum.form.SearchForm;
import com.ironman.forum.util.*;
import com.ironman.forum.vo.SearchBlogVO;
import com.ironman.forum.vo.SearchUserVO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private EsBlogRepository esBlogRepository;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnsyService ansyService;

    @Override
    public List<SearchBlogVO> searchBlog(String keywords, PageRequest pageRequest) throws GlobalException {
        List<EsBlog> esBlogList = esBlogRepository.searchBlog(keywords, pageRequest);
        if (esBlogList == null || esBlogList.size() == 0) {
            return new ArrayList<>();
        }

        List<SearchBlogVO> searchBlogVOList = new ArrayList<>(esBlogList.size());

        for (EsBlog esBlog : esBlogList) {
            SearchBlogVO searchBlogVO = BeanUtils.copy(esBlog, SearchBlogVO.class);

            //1.提取包含关键字的内容
            String content = StringUtils.isEmpty(esBlog.getContentHighlight()) ?
                    esBlog.getContent() : esBlog.getContentHighlight();
            //2.去除原生html标签
            content = IronUtil.removeHtmlTags(content);
            //3.截取指定长度内容，并且替换自定义高亮tag为<em>标签
            content = IronUtil.getSearchAbsContent(content);
            searchBlogVO.setContent(content);

            searchBlogVOList.add(searchBlogVO);
        }
        return searchBlogVOList;
    }

    @Override
    public List<SearchUserVO> searchUser(String keywords, PageRequest pageRequest) {
        List<User> userList = userDAO.pageByUsernameLike(keywords, pageRequest);
        List<SearchUserVO> searchUserVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(userList)) {
            return searchUserVOList;
        }
        long selfId = UserLoginUtil.getLoginUserId();
        for (User user : userList) {
            SearchUserVO searchUserVO = BeanUtils.copy(user, SearchUserVO.class);
            searchUserVO.setProfileUrl(commonService.concatImageUrl(user.getProfile()));
            searchUserVO.setRelation(userService.judgeUserRelation(selfId, user.getId()));
            searchUserVOList.add(searchUserVO);
        }
        return searchUserVOList;
    }

    @Override
    public List<Object> searchEntity(SearchForm form) throws GlobalException {
        //���дʹ���

        List<Object> entityVOList = new ArrayList<>();
        int type = form.getType();
        int page = form.getPage();
        int size = form.getSize();
        String keywords = form.getKeywords();
        if (StringUtils.isEmpty(keywords)) {
            return entityVOList;
        }
        if (type == ArticleTypeEnum.COMMENT.getId()) {

        } else if (type == ArticleTypeEnum.MOMENT.getId()) {

        } else if (type == ArticleTypeEnum.BLOG.getId()) {
            //博客全文搜索走ES
            List<SearchBlogVO> blogVOList = this.searchBlog(keywords, new PageRequest(page, size));
            entityVOList = this.convertToObjectList(blogVOList);
        } else if (type == ArticleTypeEnum.QUESTION.getId()) {

        } else if (type == ArticleTypeEnum.USER.getId()) {
            //用户名搜索走MySQL
            List<SearchUserVO> userVOList = this.searchUser(keywords, new PageRequest(page, size));
            entityVOList = this.convertToObjectList(userVOList);
        } else {
            throw new GlobalException(ResponseStatus.ARTICLE_TYPE_ILLEGAL);
        }

        //异步更新SearchLog,若为匿名,同样更新
        long userId = UserLoginUtil.getLoginUserId();
        this.ansyUpdateSearchLog(userId, keywords, type);


        //�첽����searchLog
        return entityVOList;
    }

    @Override
    public void ansyUpdateSearchLog(long userId, String keyword, int type) {
        SearchLog searchLog = new SearchLog();
        searchLog.setUserId(userId);
        searchLog.setKeyword(keyword);
        searchLog.setType(type);
        searchLog.setCreateTime(new Date());
        ansyService.ansyUpdateSearchLog(searchLog);
    }

    private <T> List<Object> convertToObjectList(List<T> entityList) {
        if (entityList == null || entityList.size() == 0) {
            return new ArrayList<>();
        }

        List<Object> objectList = new ArrayList<>(entityList.size());
        for (T entity : entityList) {
            objectList.add(entity);
        }

        return objectList;
    }

}
