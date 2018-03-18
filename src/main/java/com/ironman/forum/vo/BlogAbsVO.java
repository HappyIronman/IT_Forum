package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 博客简略信息（用来展示在时间轴等处）
 */
@Getter
@Setter
public class BlogAbsVO {
    private String uniqueId;
    private String userId;
    private String username;
    private String profile;
    private String title;
    private String content;
    private int likeNum;
    private int dislikeNum;
    //赞或者踩的状态，1-->未赞或踩过， 2--->已赞，3--->已踩
    private int likeCondition;
    private int commentNum;
    private int shareNum;
    private int viewNum;
    private boolean isAbstract;
    private Date createTime;
    //是否为私有
    private boolean isPrivate;
    //是否为转载
    private boolean isShare;
    //若为转载,是否原动态存在
    private boolean isExist = true;
    //若为转载，原动态作者昵称
    private String originUsername;
    //若为转载，原动态作者uniqueId
    private String originUserId;
    //若为转载，原动态题目
    private String originTitle;
    //若为转载，原动态发布时间
    private Date originCreateTime;
}
