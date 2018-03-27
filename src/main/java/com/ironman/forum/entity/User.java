package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dell on 1/23/2017.
 */
@Getter
@Setter
public class User extends BaseEntity{
	private String uniqueId;
	private String username;
	private String password;
    //0--> unknown, 1-->male, 2->female
    private int sex;
    private String phone;
    private String email;
    private String intro;
    private String profile;
    private String school;
    private int followerNum;
    private int followingNum;
    private int momentNum;
    private int blogNum;
    private int questionNum;
    private boolean disabled;
}
