package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewLogVO extends BaseLogVO {
    /**
     * ÎÄÕÂÀàĞÍ
     */
    private int articleType;

    private String articleId;

    private String articleTitle;

    private String articleContent;
}
