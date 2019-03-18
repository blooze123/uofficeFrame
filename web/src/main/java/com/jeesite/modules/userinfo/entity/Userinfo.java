/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户信息管理demoEntity
 * @author blooze
 * @version 2019-03-05
 */
@Table(
		name="userinfo", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="userid", attrName="userid", label="用户id"),
		@Column(name="username", attrName="username", label="用户姓名"),
		@Column(name="sex", attrName="sex", label="性别"),
		@Column(name="age", attrName="age", label="年龄"),
		@Column(name="phone", attrName="phone", label="手机号码"),
		@Column(name="hobby", attrName="hobby", label="个人爱好", queryType= QueryType.RIGHT_LIKE),
		@Column(name="birthday", attrName="birthday", label="生日"),
		@Column(includeEntity= DataEntity.class),
		@Column(name="del_flag", attrName="delFlag", label="del_flag"),
}, orderBy="a.update_date DESC"
)
public class Userinfo extends DataEntity<Userinfo> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// 用户id
	private String username;		// 用户姓名
	private String sex;		// 性别
	private Integer age;		// 年龄
	private String phone;		// 手机号码
	private String hobby;		// 个人爱好
	private Date birthday;		// 生日
	private String delFlag;		// del_flag
	
	public Userinfo() {
		this(null);
		System.out.println(this.getGlobal().toString());
	}

	public Userinfo(String id){
		super(id);
	}
	
	@Length(min=0, max=255, message="用户id长度不能超过 255 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=255, message="用户姓名长度不能超过 255 个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=5, message="性别长度不能超过 5 个字符")
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
	
	@Length(min=0, max=11, message="手机号码长度不能超过 11 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="个人爱好长度不能超过 255 个字符")
	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=1, message="del_flag长度不能超过 1 个字符")
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
}