package jisheng.heartbeat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import jisheng.utils.MarshallingCodeCFactory;

/**
 * 实现客户端每两秒检测系统cpu，内存使用情况发送给服务器
 * @author ASUS
 *
 */
public class Client {
	static ScheduledExecutorService excutor=Executors.newScheduledThreadPool(3);
	public static void run()  {
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try {
			Bootstrap b=new Bootstrap();
			b.group(workGroup);
			b.channel(NioSocketChannel.class);
			b.handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new MarshallingCodeCFactory().buildMarshallingDecoder());
					ch.pipeline().addLast(new MarshallingCodeCFactory().buildMarshallingEncoder());
//					ch.pipeline().addLast(new MyClientHandler());
				}
			});
			b.option(ChannelOption.SO_KEEPALIVE, false);
			ChannelFuture sync = b.connect("localhost", 8200).sync();
			GetCpuMemoryParameters req=new GetCpuMemoryParameters(sync);
			excutor.scheduleWithFixedDelay(req, 0, 2, TimeUnit.SECONDS);
			
			sync.channel().closeFuture().sync();//阻塞
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("5秒内没有发送数据了");
		} finally {
			workGroup.shutdownGracefully();
		}
	}
	public static void main(String[] args) {
		run();
	}

}
