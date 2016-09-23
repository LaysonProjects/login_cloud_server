package com.baizhi.service;

import com.baizhi.entity.BasicUser;

public interface IUserService {

	public String login(String username, String password,String appName);
	
	public String getTicket(String username,String tokenMessage,String appname);
	
	public String checkToken(String username,String tokenMessage,String appname);
	
	public String logout(String username,String appname);

}
