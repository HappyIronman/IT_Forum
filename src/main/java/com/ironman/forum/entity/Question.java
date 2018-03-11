package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question extends BaseEntity {
    private String uniqueId;
    private String content;
    private long userId;
    private int answerNum;
    private int viewNum;
    private boolean closed;
    private boolean isShare;
}
