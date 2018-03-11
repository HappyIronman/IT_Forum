package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Share extends BaseEntity {
    private long articleId;
    private long originId;
    private int type;
    private boolean deleted;
}
