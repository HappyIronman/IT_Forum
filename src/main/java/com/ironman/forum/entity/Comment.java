package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment extends BaseEntity {
    private long userId;
    private long targetId;
    //0--->回复的为评论
    //1--->回复的为moment
    //2--->回复的为blog
    //3--->回复的为question
    private int targetType;
    private String content;
    private int replyNum;
    private int likeNum;
    private int dislikeNum;
}
