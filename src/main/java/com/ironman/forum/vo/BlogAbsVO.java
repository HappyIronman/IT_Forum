package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ���ͼ�����Ϣ������չʾ��ʱ����ȴ���
 */
@Getter
@Setter
public class BlogAbsVO {
    private String uniqueId;
    private String userId;
    private String username;
    private String profileUrl;
    private String title;
    private String content;
    private int likeNum;
    private int dislikeNum;
    private int likeCondition;
    private int commentNum;
    private int shareNum;
    private int viewNum;
    private boolean isAbstract;
    private Date createTime;
    //是否私有
    private boolean isPrivate;
    //是否为分享
    private boolean isShare;
    //原博客是否存在
    private boolean isExist = true;
    //原博客作者用户名
    private String originUsername;
    //原博客作者uniqueId
    private String originUserId;
    //原博客uniqueId
    private String originBlogId;
    //原博客题目
    private String originTitle;
    //原博客发表时间
    private Date originCreateTime;
}
