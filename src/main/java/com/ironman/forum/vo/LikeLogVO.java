package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeLogVO extends BaseLogVO {
    /**
     * 标识为赞或踩
     */
    private boolean like;

    /**
     * 文章类型
     */
    private int articleType;

    private String articleId;

    private String articleTitle;

    private String articleContent;

}
