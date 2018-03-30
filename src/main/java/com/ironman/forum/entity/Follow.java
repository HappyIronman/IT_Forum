package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Follow extends BaseEntity {
    //∑€Àø
    private long followerId;
    //±ª∑€»À
    private long userId;
    private boolean disabled;
}
