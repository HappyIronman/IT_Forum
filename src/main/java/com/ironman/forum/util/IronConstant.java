package com.ironman.forum.util;

/**
 * @Author: Ironman
 * @Description:
 * @Date: Created in 11:57 2017/12/31 0031
 **/
public class IronConstant {
    public static final String SESSION_USER_KEY = "SESSION_USER_KEY";
    public static final String SESSION_ROLE_KEY = "SESSION_ROLE_KEY";
    public static final String TABLE_USER = "user";
    public static final String TABLE_COMMENT = "comment";
    public static final String TABLE_MOMENT = "moment";
    public static final String TABLE_BLOG = "blog";
    public static final String TABLE_QUESTION = "question";
    public static final String USER_PROPERTY_MOMENT_NUM = "moment_num";
    public static final String USER_PROPERTY_BLOG_NUM = "blog_num";
    public static final String USER_PROPERTY_QUESTION_NUM = "question_num";
    public static final String USER_PROPERTY_FOLLOWING_NUM = "following_num";
    public static final String USER_PROPERTY_FOLLOWER_NUM = "follower_num";
    public static final String ARTICLE_PROPERTY_LIKE_NUM = "like_num";
    public static final String ARTICLE_PROPERTY_DISLIKE_NUM = "dislike_num";
    public static final String ARTICLE_PROPERTY_SHARE_NUM = "share_num";
    public static final String ARTICLE_PROPERTY_COMMENT_NUM = "comment_num";
    public static final String ARTICLE_PROPERTY_VIEW_NUM = "view_num";
    public static final int MOMENT_MAX_LENGTH = 80;
    public static final int BLOG_MAX_LENGTH = 100;
    //����δ���޻��߲ȹ�
    public static final int LIKE_CONDITION_DEFAULT = 1;
    //�����ѱ��޹�
    public static final int LIKE_CONDITION_LIKED = 2;
    //�����ѱ��ȹ�
    public static final int LIKE_CONDITION_DISLIKED = 3;


    /**
     * �Զ����̳߳س�ʼ�߳�����
     */
    public static final int DEFAULT_POOL_SIZE = 5;

    /**
     * viewLog������⶯�������������
     */
    public static final int VIEW_LOG_MAX_CACHE_SIZE = 20;

    public static final long ANONYMOUS_USER_ID = -1L;
    public static final String ANONYMOUS_USER_UNIQUE_ID = "-1";
    public static final String ANONYMOUS_USER_NAME = "�����û�";
    public static final String ANONYMOUS_USER_PROFILE_URL = "ironman";


    public static final String ES_PRE_TAGS = "#FU#";
    public static final String ES_POST_TAGS = "#CK#";
    public static final int ES_HIGHLIGHT_MAX_SIZE = 400;
    public static final int ES_BLOG_MAX_SIZE = 150;
    public static final int TITLE_MAX_LENGTH = 30;
}
