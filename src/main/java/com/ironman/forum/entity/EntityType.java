package com.ironman.forum.entity;

public enum EntityType {
    COMMENT(0),
    MOMENT(1),
    BLOG(2),
    QUESTION(3),
    USER(4);

    EntityType(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }
}
