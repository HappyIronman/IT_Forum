package com.ironman.forum.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MomentVO {
    //id字段必须存在，用于aop
    @JsonIgnore
    private long id;
    private String uniqueId;
    private String userId;
    private String username;
    private String profileUrl;
    private String content;
    private int likeNum;
    private int dislikeNum;
    //赞或者踩的状态，1-->未赞或踩过， 2--->已赞，3--->已踩
    private int likeCondition;
    private int commentNum;
    private int shareNum;
    private int viewNum;
//    private boolean isAbstract;
    private Date createTime;
    //是否为私有
    private boolean isPrivate;
    //是否带有图片
    private boolean isContainPic;
    //是否为转载
    private boolean isShare;
    //若为转载,是否原动态存在
    private boolean isExist = true;
    //若为转载，原动态作者昵称
    private String originUsername;
    //若为转载，原动态作者uniqueId
    private String originUserId;
    //若为转载，原动态内容
    private String originContent;
    //若为转载，原动态发布时间
    private Date originCreateTime;
    //若带有图片,图片url集合
    private List<String> picUrlList;
}
