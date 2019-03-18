/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userinfosecond.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

/**
 * 第二版用户名，测试多租户Entity
 * @author blooze
 * @version 2019-03-08
 */
@Table(name="userinfosecond", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="username", attrName="username", label="username"),
		@Column(name="sex", attrName="sex", label="sex"),
		@Column(name="age", attrName="age", label="age"),
		@Column(name="phone", attrName="phone", label="phone"),
		@Column(includeEntity= DataEntity.class),
		@Column(includeEntity= BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class Userinfosecond extends DataEntity<Userinfosecond> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// userid
	private String username;		// username
	private String sex;		// sex
	private Integer age;		// age
	private Long phone;		// phone
	
	public Userinfosecond() {
		this(null);
	}

	public Userinfosecond(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="userid长度不能超过 255 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=255, message="username长度不能超过 255 个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=5, message="sex长度不能超过 5 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
}