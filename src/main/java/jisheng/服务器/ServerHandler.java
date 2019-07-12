package jisheng.服务器;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 接收请求
//		HttpRequest req = (HttpRequest) msg;
		System.out.println(msg);
		System.out.println("进来了");
		// 返回响应
//		ctx.writeAndFlush(Unpooled.copiedBuffer("HTTP/1.1 200 ok".getBytes()));
//		ctx.writeAndFlush(Unpooled.copiedBuffer("Content-Type:text/html;charset=utf-8".getBytes()));
//		ctx.writeAndFlush(Unpooled.copiedBuffer("success".getBytes()));
//		ctx.writeAndFlush(Unpooled.copiedBuffer("success".getBytes()).readableBytes());
		// System.out.println(msg);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
