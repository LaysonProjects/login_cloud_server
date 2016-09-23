package com.baizhi.vo;

import java.io.Serializable;

public class TokenReturnMessage implements Serializable{

	private String statusCode;
	private String message;
	private String tokenMessage;

	public TokenReturnMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TokenReturnMessage(String statusCode, String message,
			String tokenMessage) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.tokenMessage = tokenMessage;
	}
	
	public TokenReturnMessage(String statusCode, String message) {
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

	public String getTokenMessage() {
		return tokenMessage;
	}

	public void setTokenMessage(String tokenMessage) {
		this.tokenMessage = tokenMessage;
	}

	@Override
	public String toString() {
		return "TokenReturnMessage [statusCode=" + statusCode + ", message="
				+ message + ", tokenMessage=" + tokenMessage + "]";
	}

}
