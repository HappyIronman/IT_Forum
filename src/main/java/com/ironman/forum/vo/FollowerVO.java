package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FollowerVO {
    private String uniqueId;
    private String username;
    private String profile;
    private int followerNum;
    private int followingNum;
    private int momentNum;
    private int blogNum;
    private int questionNum;
    private Date followDate;
    private int relation;
}
