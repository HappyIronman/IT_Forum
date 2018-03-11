package com.ironman.forum.entity;

public enum ArticleType {
    COMMENT(0),
    MOMENT(1),
    BLOG(2),
    QUESTION(3);

    ArticleType(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }
}
