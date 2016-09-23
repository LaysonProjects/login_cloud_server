package com.baizhi.dao;

import org.apache.ibatis.annotations.Param;

import com.baizhi.entity.BasicUser;
import com.sun.org.glassfish.gmbal.ParameterNames;


public interface IUserDAO {

	public BasicUser login(@Param("username") String userName,@Param("password") String password);
	
}
