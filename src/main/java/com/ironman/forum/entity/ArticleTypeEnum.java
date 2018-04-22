package com.ironman.forum.entity;

public enum ArticleTypeEnum {
    COMMENT(0),
    MOMENT(1),
    BLOG(2),
    QUESTION(3),
    USER(4);
    private int id;

    ArticleTypeEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
