package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchUserVO {
    private String uniqueId;
    private String username;
    private String profileUrl;
    private int followerNum;
    private int followingNum;
    private int momentNum;
    private int blogNum;
    private int questionNum;
    private int relation;
}
