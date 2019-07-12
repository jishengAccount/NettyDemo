package jisheng.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jisheng.utils.MarshallingCodeCFactory;

/**
 * 实现客户端每两秒检测系统cpu，内存使用情况发送给服务器
 * @author ASUS
 *
 */
public class Server {
	public static void run() throws Exception{
		EventLoopGroup boosGroup=new NioEventLoopGroup();
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try {
			ServerBootstrap sBoot=new ServerBootstrap();
			sBoot.group(boosGroup, workGroup);
			sBoot.channel(NioServerSocketChannel.class);
			sBoot.option(ChannelOption.SO_BACKLOG, 128);
			sBoot.childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new MarshallingCodeCFactory().buildMarshallingDecoder());
					ch.pipeline().addLast(new MarshallingCodeCFactory().buildMarshallingEncoder());
					ch.pipeline().addLast(new ServerHandler());
				}
			});
			ChannelFuture sync = sBoot.bind(8200).sync();
			sync.channel().closeFuture().sync();
			
			
		} finally {
			boosGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}
	public static void main(String[] args) throws Exception {
		run();
	}
}
