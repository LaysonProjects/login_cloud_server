package jiangzz.rpc.server;

import java.lang.reflect.Method;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import jiangzz.rpc.model.MethodInvokeMeta;
/**
 * 多线程共享
 * @author Administrator
 *
 */
@Sharable
public class ServerChannelHandlerAdapter extends ChannelHandlerAdapter {
	private RequestDispatcher dispatcher;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		MethodInvokeMeta invokeMeta=(MethodInvokeMeta) msg;
		dispatcher.dispatcher(ctx, invokeMeta);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}

	public void setDispatcher(RequestDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	
}
