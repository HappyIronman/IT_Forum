package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeLogVO extends BaseLogVO {
    /**
     * ��ʶΪ�޻��
     */
    private boolean like;

    /**
     * ��������
     */
    private int articleType;

    private String articleId;

    private String articleTitle;

    private String articleContent;

}
