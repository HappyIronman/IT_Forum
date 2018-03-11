package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BlogVO {
    private String uniqueId;
    private String userId;
    private String username;
    private String profile;
    private String title;
    private String content;
    private int likeNum;
    private int dislikeNum;
    private int commentNum;
    private int shareNum;
    private int viewNum;
    private boolean isAbstract;
    private Date createTime;
    //�Ƿ�Ϊ˽��
    private boolean isPrivate;
    //�Ƿ�Ϊת��
    private boolean isShare;
    //��Ϊת��,�Ƿ�ԭ��̬����
    private boolean isExist = true;
    //��Ϊת�أ�ԭ��̬�����ǳ�
    private String originUsername;
    //��Ϊת�أ�ԭ��̬����uniqueId
    private String originUserId;
    //��Ϊת�أ�ԭ��̬����
    private String originTitle;
    //��Ϊת�أ�ԭ��̬����ʱ��
    private Date originCreateTime;
}
