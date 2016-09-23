package jiangzz.rpc.server;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;import io.netty.channel.group.ChannelGroupFutureListener;
import jiangzz.rpc.model.MethodInvokeMeta;
import jiangzz.rpc.model.NullWritable;
/**
 * 请求分排器
 * @author Administrator
 *
 */
public class RequestDispatcher  implements ApplicationContextAware{
     private ExecutorService executorService=Executors.newFixedThreadPool(1024);
     private ApplicationContext app;
     public  void dispatcher(final ChannelHandlerContext ctx,final MethodInvokeMeta invokeMeta){
    	executorService.submit(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				ChannelFuture f=null;
				 try {
			    	   Class<?> interfaceClass = invokeMeta.getInterfaceClass();
			    	   String name=invokeMeta.getName();
			    	   Object[] args=invokeMeta.getArgs();
			    	   Class<?>[] parameterTypes = invokeMeta.getParameterTypes();
			    	   Object targetObject = app.getBean(interfaceClass);
					   Method method=targetObject.getClass().getMethod(name,parameterTypes);
					   Object obj = method.invoke(targetObject, args);
					   if(obj==null){
						    f=ctx.writeAndFlush(NullWritable.nullWritable());
					   }else{
						    f = ctx.writeAndFlush(obj);
					   }
					   f.addListener(ChannelFutureListener.CLOSE);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						f.addListener(ChannelFutureListener.CLOSE);
					}
			}
		});
     }
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// TODO Auto-generated method stub
		this.app=ctx;
	}
}
