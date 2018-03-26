package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image extends BaseEntity {
    private String name;
    private long userId;
    private long articleId;
    private int type;
    private boolean deleted;
}
