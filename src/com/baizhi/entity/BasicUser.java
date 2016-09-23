package com.baizhi.entity;

import java.io.Serializable;

public class BasicUser implements Serializable{

	private Integer id;
	private String userName;
	private String password;

	public BasicUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BasicUser(Integer id, String userName, String password) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "BasicUser [id=" + id + ", userName=" + userName + ", password="
				+ password + "]";
	}

}
