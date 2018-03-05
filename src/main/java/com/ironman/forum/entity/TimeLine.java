package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimeLine extends BaseEntity {
    private long userId;
    private long eventId;
    //0--->为评论
    //1--->为moment
    //2--->为blog
    //3--->为question
    private int type;
    private boolean isSelf;
    private boolean isNew;
}
