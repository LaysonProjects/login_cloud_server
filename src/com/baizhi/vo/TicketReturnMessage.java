package com.baizhi.vo;

import java.io.Serializable;

public class TicketReturnMessage implements Serializable {

	private String statusCode;
	private String message;
	private Ticket ticket;

	public TicketReturnMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TicketReturnMessage(String statusCode, String message, Ticket ticket) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.ticket = ticket;
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

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "TicketReturnMessage [statusCode=" + statusCode + ", message="
				+ message + ", ticket=" + ticket + "]";
	}

}
