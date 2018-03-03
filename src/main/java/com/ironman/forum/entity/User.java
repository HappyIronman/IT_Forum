package com.ironman.forum.entity;

/**
 * Created by dell on 1/23/2017.
 */
public class User extends BaseEntity{
	private String uniqueId;
	private String username;
	private String password;
	private String introduction;
	private String picPath;
	private String mobile;
	private String email;

	public String getUniqueId(){
		return uniqueId;
	}

	public void setUniqueId(String uniqueId){
		this.uniqueId = uniqueId;
	}

	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getIntroduction(){
		return introduction;
	}

	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}

	public String getPicPath(){
		return picPath;
	}

	public void setPicPath(String picPath){
		this.picPath = picPath;
	}

	public String getMobile(){
		return mobile;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	@Override
	public String toString(){
		return "User{" +
				"uniqueId='" + uniqueId + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", introduction='" + introduction + '\'' +
				", picPath='" + picPath + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
