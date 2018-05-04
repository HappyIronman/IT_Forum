package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class CommentVO {
    private String uniqueId;
    //uniqueId
    private String userId;
    private String username;
    private String profileUrl;
    private int type;
    private String content;
    private int commentNum;
    private int likeNum;
    private int dislikeNum;
    private int likeCondition;
    private Date createTime;
}
