package com.ironman.forum.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuestionVO {
    //id字段必须存在，用于aop
    @JsonIgnore
    private long id;
    private String uniqueId;
    private String userId;
    private String username;
    private String profileUrl;
    private String title;
    private String content;
    private int commentNum;
    private int viewNum;
    private int likeNum;
    private int dislikeNum;
    private boolean closed;
    private Date createTime;
}
