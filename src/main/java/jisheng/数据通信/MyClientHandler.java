package jisheng.数据通信;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class MyClientHandler extends ChannelInboundHandlerAdapter {
	
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 发送信息
		ctx.write(Unpooled.copiedBuffer("这是client发出的信息".getBytes()));
		ctx.flush();
	
	}
	
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	
		// 收到响应并打印
		ByteBuf b=(ByteBuf) msg;
		byte[] bytes=new byte[b.readableBytes()];
		try {
				b.readBytes(bytes);
				System.out.println(new String(bytes,"utf-8"));
		} finally {
			b.release();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
