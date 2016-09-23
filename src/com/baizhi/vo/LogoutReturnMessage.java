package com.baizhi.vo;

import java.io.Serializable;

public class LogoutReturnMessage implements Serializable {

	private String statusCode;
	private String message;

	public LogoutReturnMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogoutReturnMessage(String statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "LogoutReturnMessage [statusCode=" + statusCode + ", message="
				+ message + "]";
	}

}
