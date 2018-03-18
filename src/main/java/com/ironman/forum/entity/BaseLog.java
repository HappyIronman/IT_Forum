package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseLog extends BaseEntity {
    private long userId;
    private long targetId;
    //0--->为评论
    //1--->为moment
    //2--->为blog
    //3--->为question
    private int type;
    private boolean disabled;
}
