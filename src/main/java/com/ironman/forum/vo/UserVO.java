package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserVO {
    private String uniqueId;
    private String username;
    private String password;
    //0--> unknown, 1-->male, 2->female
    private int sex;
    private String phone;
    private String email;
    private String intro;
    private String profile;
    private int followerNum;
    private int followingNum;
    private int momentNum;
    private int blogNum;
    private int questionNum;
    private String school;
    private Date createTime;
}
