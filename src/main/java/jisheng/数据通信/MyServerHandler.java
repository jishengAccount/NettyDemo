package jisheng.数据通信;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInActive");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf b = (ByteBuf) msg;
		/*方法一：*/
//		StringBuffer sBuffer = new StringBuffer();
//		while (b.isReadable()) {
//			sBuffer.append((char) b.readByte());
//		}
//		System.out.println(sBuffer);
		/*方法二*/
		byte[] bytes=new byte[b.readableBytes()];
//		byte[] bytes=new byte[1024];//不行为什么？
		b.readBytes(bytes);
		String str=new String(bytes);
		System.out.println(str);
		b.release();
//		响应数据
		ctx.write(Unpooled.copiedBuffer("发送给客户端的响应".getBytes()));
		ctx.flush();
		//会报错
//		ByteBuf b1=ctx.alloc().buffer(2000);
//		ByteBuf response = b1.readBytes(("发送给客户端的响应").getBytes());
//		ctx.write(response);
//		ctx.flush();

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("completed");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
