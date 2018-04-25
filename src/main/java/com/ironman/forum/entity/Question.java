package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question extends Article {
    private String title;
    private int answerNum;
    private int viewNum;
    private boolean closed;
    private boolean isShare;
}
