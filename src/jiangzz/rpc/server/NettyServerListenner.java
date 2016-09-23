package jiangzz.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import jiangzz.rpc.ObjectCodec;
/**
 * 服务启动
 * @author Administrator
 *
 */
public class NettyServerListenner {
	 //创建bootstrap
	ServerBootstrap sbt=new ServerBootstrap();
	EventLoopGroup boss=new NioEventLoopGroup();
	EventLoopGroup work=new NioEventLoopGroup();
	private int port;
	private ServerChannelHandlerAdapter channelHandlerAdapter;
	public NettyServerListenner(int port){
		sbt.group(boss, work);
		//设置服务处理类
		sbt.channel(NioServerSocketChannel.class);
		this.port=port;
	}
	public void start(){
		new Thread(){
			public void run() {
				try {
					//设置事件处理
					sbt.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
							pipeline.addLast(new LengthFieldPrepender(2));
							pipeline.addLast(new ObjectCodec());	
							pipeline.addLast(channelHandlerAdapter);	
							
						}
					});
					System.out.println("服务器启动，监听 @"+port+" ....");
					ChannelFuture f = sbt.bind(port).sync();
					f.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					boss.shutdownGracefully();
					work.shutdownGracefully();
				}
			};
		}.start();
	}
	public void close(){
		System.out.println("关闭服务器....");
		//优雅退出
		boss.shutdownGracefully();
		work.shutdownGracefully();
	}
	public void setChannelHandlerAdapter(ServerChannelHandlerAdapter channelHandlerAdapter) {
		this.channelHandlerAdapter = channelHandlerAdapter;
	}
	
}
