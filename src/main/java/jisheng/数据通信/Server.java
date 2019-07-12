package jisheng.数据通信;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Server {
	public static void run() throws Exception {
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try {
			ServerBootstrap sBoot=new ServerBootstrap();
			sBoot.group(bossGroup, workGroup);
			sBoot.channel(NioServerSocketChannel.class);
			sBoot.childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
//					ch.pipeline().addLast(new ReadTimeoutHandler(5));
					ch.pipeline().addLast(new MyServerHandler());
				}
			});
			sBoot.option(ChannelOption.SO_BACKLOG, 128);
			sBoot.childOption(ChannelOption.SO_KEEPALIVE, false);
			ChannelFuture sync = sBoot.bind(8200).sync();
			
			
			sync.channel().closeFuture().sync();//阻塞
			
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		run();
	}

}
