package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Blog extends Article {
    private String title;
    private int shareNum;
    private int commentNum;
    private int viewNum;
    private boolean isPrivate;
    private boolean isShare;
}
