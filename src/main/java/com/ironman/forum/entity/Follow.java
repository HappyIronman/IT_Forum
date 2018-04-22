package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Follow extends BaseEntity {
    //粉丝
    private long followerId;
    //被粉人
    private long userId;
    private boolean disabled;
}
