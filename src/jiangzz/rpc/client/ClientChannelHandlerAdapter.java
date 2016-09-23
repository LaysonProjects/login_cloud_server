package jiangzz.rpc.client;

import java.util.Date;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import jiangzz.rpc.model.MethodInvokeMeta;
import jiangzz.rpc.model.NullWritable;
public class ClientChannelHandlerAdapter extends ChannelHandlerAdapter {
    private MethodInvokeMeta methodInvokeMeta;
    private CustomChannelInitializer channelInitializer;
	public ClientChannelHandlerAdapter(MethodInvokeMeta invokeMeta, CustomChannelInitializer channelInitializer) {
		// TODO Auto-generated constructor stub
		this.methodInvokeMeta=invokeMeta;
		this.channelInitializer=channelInitializer;
	}
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.writeAndFlush(methodInvokeMeta);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		channelInitializer.setResponse(msg);
	}
}
