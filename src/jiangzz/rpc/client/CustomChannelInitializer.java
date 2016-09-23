package jiangzz.rpc.client;

import jiangzz.rpc.ObjectCodec;
import jiangzz.rpc.model.MethodInvokeMeta;
import jiangzz.rpc.model.NullWritable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {
	private MethodInvokeMeta invokeMeta;
	private Object response;
	public CustomChannelInitializer(MethodInvokeMeta invokeMeta){
		this.invokeMeta=invokeMeta;
	}
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LengthFieldPrepender(2));
		pipeline.addLast(new LengthFieldBasedFrameDecoder(1024*1024, 0, 2, 0, 2));
		pipeline.addLast(new ObjectCodec());
		pipeline.addLast(new ClientChannelHandlerAdapter(invokeMeta,this));
		
	}
	public Object getResponse() {
		if(response instanceof NullWritable){
			return null;
		}
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
    
	

}
