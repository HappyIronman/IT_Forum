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
    //4--->为user
    // 若为user,即为新增粉丝事件,targetId为被粉人id,否则为对应文章id

    //EntityTypeEnum
    private int type;
    private boolean disabled;
}
