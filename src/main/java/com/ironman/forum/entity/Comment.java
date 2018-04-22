package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment extends Article {
    private long replyId;
    private int type;
    private int commentNum;
}
