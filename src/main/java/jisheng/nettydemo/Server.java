package jisheng.nettydemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	private int port;

	public Server(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		// 声明组（线程池）
		EventLoopGroup bossGroup = new NioEventLoopGroup();//相当于selector
		EventLoopGroup workGroup = new NioEventLoopGroup();//处理连接的客户端
		try {
			// 声明服务端启动器
			ServerBootstrap serverBoot = new ServerBootstrap();
			// 绑定组
			serverBoot.group(bossGroup, workGroup);
			// 绑定管道
			serverBoot.channel(NioServerSocketChannel.class);
			// 初始化处理handler
			serverBoot.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel arg0) throws Exception {
					arg0.pipeline().addLast(new MyServerHandler());
				}
			});
			serverBoot.option(ChannelOption.SO_BACKLOG, 128);
			serverBoot.childOption(ChannelOption.SO_KEEPALIVE, true);//是否保持一致监控状态
			// 绑定端口
			ChannelFuture future = serverBoot.bind(port).sync();
			
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args) {
		try {
			new Server(8700).run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
