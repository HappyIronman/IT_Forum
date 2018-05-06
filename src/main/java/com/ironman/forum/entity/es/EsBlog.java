package com.ironman.forum.entity.es;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Getter
@Setter
@Document(indexName = "forum", type = "blog")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EsBlog {
    @Id
    protected long id;
    private String uniqueId;
    private long userId;
    private String userUniqueId;
    private String username;
    private String title;
    private String titleHighlight;
    private String content;
    private String contentHighlight;
    private int likeNum;
    private int dislikeNum;
    private int shareNum;
    private int commentNum;
    private int viewNum;
    private boolean isPrivate;
    private boolean isShare;
    private boolean deleted;
    protected Date createTime;
}