package com.baizhi.vo;

import java.util.Date;

public class Ticket {

	private String username;
	private Date timestamp;
	private long validity;
	private String appname;

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ticket(String username, Date timestamp, String appname) {
		super();
		this.username = username;
		this.timestamp = timestamp;
		this.appname = appname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getValidity() {
		return validity;
	}

	public void setValidity(long validity) {
		this.validity = validity;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	@Override
	public String toString() {
		return "Ticket [username=" + username + ", timestamp=" + timestamp
				+ ", validity=" + validity + ", appname=" + appname + "]";
	}

}
