package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Moment extends Article {
    private int commentNum;
    private int shareNum;
    private int viewNum;
    private boolean isPrivate;
    private boolean isShare;
    private boolean isContainPic;
}
