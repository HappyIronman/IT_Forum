package com.ironman.forum.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Ironman
 * @Description:
 * @Date: Created in 16:15 2017/12/31 0031
 **/
@Getter
@Setter
public class BaseEntity {
	protected long id;
	protected Date createTime;

	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
