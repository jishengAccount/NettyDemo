package jisheng.nettydemo;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client implements Runnable{
	@Override
	public  void run() {
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try {
		Bootstrap b=new Bootstrap();
		b.group(workGroup);
		b.channel(NioSocketChannel.class);
		b.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel arg0) throws Exception {
				arg0.pipeline().addLast(new MyClientHandler());
			}
		});
		b.option(ChannelOption.SO_KEEPALIVE, false);
		ChannelFuture future = b.connect("localhost", 8700).sync();
//		Thread.sleep(1000);
//		future.channel().writeAndFlush(Unpooled.copiedBuffer("这是客户端发送的请求1".getBytes()));
//		Thread.sleep(1000);
//		future.channel().writeAndFlush(Unpooled.copiedBuffer("这是客户端发送的请求2".getBytes()));
//		Thread.sleep(1000);
//		future.channel().writeAndFlush(Unpooled.copiedBuffer("这是客户端发送的请求3".getBytes()));
		future.channel().closeFuture().sync();//阻塞
		}catch(Exception e) {
			e.printStackTrace();
		
		} finally {
			workGroup.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) {
//		for (int i = 0; i < 100; i++) {
			new Thread(new Client()).start();
//		}
		
	}
	

}
