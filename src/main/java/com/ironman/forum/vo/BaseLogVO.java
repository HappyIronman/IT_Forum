package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseLogVO {
    /**
     * 关注我的人的uniqueId
     */
    protected String userId;

    protected String username;

    protected String profile;

    /**
     * 对应AboutMe.LogType
     */
    protected int type;

    protected boolean isNew;

    protected Date createTime;
}
