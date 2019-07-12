package jisheng.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class MyClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 向服务器发送信息
		ctx.write(Unpooled.copiedBuffer("这是client发出的信息".getBytes()));
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		// 收到服务器的响应并打印
		ByteBuf b=(ByteBuf) msg;
		System.out.println(b.toString(CharsetUtil.UTF_8));
//		byte[] bytes=new byte[b.readableBytes()];
//		try {
//				b.readBytes(bytes);
//				System.out.println(new String(bytes));
//		} finally {
//			ReferenceCountUtil.release(msg);
//		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
