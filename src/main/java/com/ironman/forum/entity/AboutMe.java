package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AboutMe extends BaseEntity {
    private long userId;
    private long logId;
    private int type;
    private boolean isNew;
    private boolean deleted;


    public enum LogType {

        LIKE_LOG(0),
        VIEW_LOG(1),
        COMMENT(2),
        FOLLOW(3);

        LogType(int id) {
            this.id = id;
        }

        private int id;

        public int getId() {
            return id;
        }
    }
}
