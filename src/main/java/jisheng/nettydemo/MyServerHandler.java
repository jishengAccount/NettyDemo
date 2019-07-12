package jisheng.nettydemo;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		接收客户端的数据
		ByteBuf b=(ByteBuf) msg;
		System.out.println(b.toString(CharsetUtil.UTF_8));
		//对接受的数据进行处理
//		byte[] bytes=new byte[b.readableBytes()];
//		try {
//				b.readBytes(bytes);
//				System.out.println(new String(bytes));
//		} finally {
//			ReferenceCountUtil.release(msg);
//		}
//		响应的数据
		ctx.write(Unpooled.copiedBuffer("这是server响应的数据".getBytes()));
		ctx.flush();
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}

}
