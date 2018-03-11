package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimeLine extends BaseEntity {
    private long userId;
    private long eventId;
    private int type;
    private boolean isSelf;
    private boolean isNew;
    private boolean deleted;
}
