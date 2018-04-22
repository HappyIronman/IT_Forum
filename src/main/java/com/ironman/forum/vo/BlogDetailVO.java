package com.ironman.forum.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ����������Ϣ������չʾ�ڲ�������ҳ��
 */
@Getter
@Setter
public class BlogDetailVO {
    //id�ֶα�����ڣ����ڴ�����
    @JsonIgnore
    private long id;
    private String uniqueId;
    private String userId;
    private String username;
    private String title;
    private String content;
    private int likeNum;
    private int dislikeNum;
    private int commentNum;
    private int shareNum;
    private int viewNum;
    private int likeCondition;
    private Date createTime;
    //�Ƿ�Ϊ˽��
    private boolean isPrivate;
    //�Ƿ�Ϊת��
    private boolean isShare = false;
    //��Ϊת��,�Ƿ�ԭ���ʹ���
    private boolean isExist = true;
    //��Ϊת�أ�ԭ���������ǳ�
    private String originUsername;
    //��Ϊת�أ�ԭ��������uniqueId
    private String originUserId;
    //��Ϊת�أ�ԭ������Ŀ
    private String originTitle;
    //��Ϊת�أ�ԭ��������
    private String originContent;
    //��Ϊת�أ�ԭ���ͷ���ʱ��
    private Date originCreateTime;
}
