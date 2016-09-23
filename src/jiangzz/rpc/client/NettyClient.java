package jiangzz.rpc.client;

import java.lang.reflect.Method;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import jiangzz.rpc.model.MethodInvokeMeta;
import jiangzz.rpc.ObjectCodec;
/**
 * 发送消息 网络
 * @author Administrator
 *
 */
public class NettyClient {
	private Bootstrap bootstrap;
	private EventLoopGroup worker;
	private String host;
	private int port;
	private int MAX_RETRY_TIMES=10;
	public NettyClient(String host,int port){
		this.host=host;
		this.port=port;
		bootstrap=new Bootstrap();
		worker=new NioEventLoopGroup();
		bootstrap.group(worker);
		bootstrap.channel(NioSocketChannel.class);
	}
	
	public Object remoteCall(final MethodInvokeMeta cmd,int retry){
		try {
			CustomChannelInitializer handler = new CustomChannelInitializer(cmd);
			bootstrap.handler(handler);
			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().closeFuture().sync();
			Object response = handler.getResponse();
			return response;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			retry++;
			if(retry>MAX_RETRY_TIMES){
				throw new RuntimeException("调用出错...");
			}else{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.err.println("尝试 "+retry+" 次，后失败");
				return remoteCall(cmd,retry);
			}
			
		}
	}
	public void close(){
		System.out.println("关闭资源");
		worker.shutdownGracefully();
	}
	
}
