package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 博客详情信息（用来展示在博客详情页）
 */
@Getter
@Setter
public class BlogDetailVO {
    private String uniqueId;
    private String userId;
    private String username;
    private String title;
    private String content;
    private int likeNum;
    private int dislikeNum;
    private int commentNum;
    private int shareNum;
    private int viewNum;
    private Date createTime;
    //是否为私有
    private boolean isPrivate;
    //是否为转载
    private boolean isShare = false;
    //若为转载,是否原博客存在
    private boolean isExist = true;
    //若为转载，原博客作者昵称
    private String originUsername;
    //若为转载，原博客作者uniqueId
    private String originUserId;
    //若为转载，原博客题目
    private String originTitle;
    //若为转载，原博客内容
    private String originContent;
    //若为转载，原博客发布时间
    private Date originCreateTime;
}
