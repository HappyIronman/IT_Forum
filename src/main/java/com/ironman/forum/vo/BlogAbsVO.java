package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ���ͼ�����Ϣ������չʾ��ʱ����ȴ���
 */
@Getter
@Setter
public class BlogAbsVO {
    private String uniqueId;
    private String userId;
    private String username;
    private String profileUrl;
    private String title;
    private String content;
    private int likeNum;
    private int dislikeNum;
    //�޻��߲ȵ�״̬��1-->δ�޻�ȹ��� 2--->���ޣ�3--->�Ѳ�
    private int likeCondition;
    private int commentNum;
    private int shareNum;
    private int viewNum;
    private boolean isAbstract;
    private Date createTime;
    //�Ƿ�Ϊ˽��
    private boolean isPrivate;
    //�Ƿ�Ϊת��
    private boolean isShare;
    //��Ϊת��,�Ƿ�ԭ���ʹ���
    private boolean isExist = true;
    //��Ϊת�أ�ԭ���������ǳ�
    private String originUsername;
    //��Ϊת�أ�ԭ��������uniqueId
    private String originUserId;
    //��Ϊת�أ�ԭ������Ŀ
    private String originTitle;
    //��Ϊת�أ�ԭ���ͷ���ʱ��
    private Date originCreateTime;
}
