package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question extends Article {
    private String title;
    //回答数量
    private int commentNum;
    private int viewNum;
    private boolean closed;
}
