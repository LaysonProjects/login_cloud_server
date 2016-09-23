package com.baizhi.vo;

import java.util.Set;

public class Token {

	private String username;
	private String password;
	private Set<String> appname;

	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Token(String username, String password, Set<String> appname) {
		super();
		this.username = username;
		this.password = password;
		this.appname = appname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getAppname() {
		return appname;
	}

	public void setAppname(Set<String> appname) {
		this.appname = appname;
	}

	@Override
	public String toString() {
		return "Token [username=" + username + ", password=" + password
				+ ", appname=" + appname + "]";
	}

}
