package jisheng.数据通信;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 没有数据发送时自动断开连接使用了
 * @author ASUS
 *
 */
public class Client implements Runnable{
	
	public  void run() {
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try {
			Bootstrap b=new Bootstrap();
			b.group(workGroup);
			b.channel(NioSocketChannel.class);
			b.handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new ReadTimeoutHandler(5));//客户端在5秒之内没有像服务器发送数据自动断开连接
					ch.pipeline().addLast(new MyClientHandler());
				}
			});
			b.option(ChannelOption.SO_KEEPALIVE, false);
			ChannelFuture sync = b.connect("localhost", 8200).sync();
			
//			sync.channel().writeAndFlush(Unpooled.copiedBuffer("客户端发的请求1".getBytes()));
//			Thread.sleep(2000);
//			sync = b.connect("localhost", 8200).sync();
//			sync.channel().writeAndFlush(Unpooled.copiedBuffer("客户端发的请求2".getBytes()));
			
			sync.channel().closeFuture().sync();//阻塞
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("5秒内没有发送数据了");
		} finally {
			workGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Client c=new Client();
		c.run();
//		Thread.sleep(2000);
//		new Thread(c).start();;
		
	}

}
