package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Follow extends BaseEntity {
    private long followerId;
    private long userId;
    private boolean disabled;
}
