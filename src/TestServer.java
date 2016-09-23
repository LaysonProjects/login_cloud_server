

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baizhi.service.IUserService;

import io.netty.bootstrap.ServerBootstrap;

public class TestServer {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		System.in.read();
	}
	
}
