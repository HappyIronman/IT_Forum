package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMoment extends BaseEntity {
    private long userId;
    private long momentId;
    private String reason;
    //    0-->原创，1-->转载
    private int type;
    private boolean isPrivate;
    private boolean deleted;

    public enum Type {
        ORIGINAL(0), SHARE(1);
        private int type;

        Type(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
