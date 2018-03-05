package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Moment extends BaseEntity {
    private String uniqueId;
    private String content;
    private long userId;
    private int likeNum;
    private int dislikeNum;
    private int commentNum;
    private int viewNum;
    private boolean isPrivate;
    private boolean deleted;
}
