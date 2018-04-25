package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentLogVO extends BaseLogVO {
    private String uniqueId;

    //uniqueId
    private String replyId;
    private int type;
    //原文章的标题(若为动态，则取简略内容)
    private String replyTitle;
    private String content;
}
