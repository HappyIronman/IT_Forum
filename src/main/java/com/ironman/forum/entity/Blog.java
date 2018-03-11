package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Blog extends BaseEntity {
    private String uniqueId;
    private long userId;
    private String title;
    private String content;
    private int likeNum;
    private int dislikeNum;
    private int commentNum;
    private int viewNum;
    private boolean isPrivate;
    private boolean isShare;
    private boolean deleted;
}
