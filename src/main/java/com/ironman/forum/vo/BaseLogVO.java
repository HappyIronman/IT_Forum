package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseLogVO {
    /**
     * ��ע�ҵ��˵�uniqueId
     */
    protected String userId;

    protected String username;

    protected String profile;

    /**
     * ��ӦAboutMe.LogType
     */
    protected int type;

    protected boolean isNew;

    protected Date createTime;
}
