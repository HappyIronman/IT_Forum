package com.ironman.forum.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SearchBlogVO {
    @JsonIgnore
    protected long id;
    private String uniqueId;
    private String userUniqueId;
    private String username;
    private String title;
    private String content;
    private int likeNum;
    private int dislikeNum;
    private int shareNum;
    private int commentNum;
    private int viewNum;
    private boolean isShare;
    protected Date createTime;
}
