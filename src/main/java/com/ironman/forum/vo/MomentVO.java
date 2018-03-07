package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MomentVO {
    private String uniqueId;
    private String content;
    private int likeNum;
    private int dislikeNum;
    private int commentNum;
    private int shareNum;
    private int viewNum;
    //是否为私有
    private boolean isPrivate;
    //若为转载,是否原评论存在
    private boolean isExist = true;
    private String reason;
    private int type;
    private String username;
    private String profile;
    private Date createTime;
}
