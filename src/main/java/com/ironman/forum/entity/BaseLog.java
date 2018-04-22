package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseLog extends BaseEntity {
    private long userId;
    private long targetId;
    //0--->Ϊ����
    //1--->Ϊmoment
    //2--->Ϊblog
    //3--->Ϊquestion
    //4--->Ϊuser
    // ��Ϊuser,��Ϊ������˿�¼�,targetIdΪ������id,����Ϊ��Ӧ����id

    //ArticleTypeEnum
    private int type;
    private boolean disabled;
}
