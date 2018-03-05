package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchLog extends BaseEntity {
    private String keyword;
    private long userId;
    private long hit;
}
