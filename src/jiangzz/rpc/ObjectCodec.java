package jiangzz.rpc;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import jiangzz.rpc.util.ObjectSerializerUtils;

public class ObjectCodec extends MessageToMessageCodec<ByteBuf,Object> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytes=new byte[msg.readableBytes()];
		msg.readBytes(bytes);
		Object deSerilizer = ObjectSerializerUtils.deSerilizer(bytes);
		out.add(deSerilizer);
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		byte[] data = ObjectSerializerUtils.serilizer(msg);
		ByteBuf buf=Unpooled.buffer();
		buf.writeBytes(data);
		out.add(buf);
	}

}
