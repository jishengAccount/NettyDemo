package jisheng.heartbeat;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("进来了");
		ClientRequest info=(ClientRequest)msg;
		System.out.println("--------------------------------------------");
		System.out.println("当前主机ip为: " + info.getIp());
		System.out.println("当前主机cpu情况: ");
		Map<String, Object> cpu = info.getCpu();
		System.out.println("总使用率: " + cpu.get("combined"));
		System.out.println("用户使用率: " + cpu.get("user"));
		System.out.println("系统使用率: " + cpu.get("sys"));
		System.out.println("等待率: " + cpu.get("wait"));
		System.out.println("空闲率: " + cpu.get("idle"));
		
		System.out.println("当前主机memory情况: ");
		Map<String, Object> memory = info.getMemory();
		System.out.println("内存总量: " + memory.get("total"));
		System.out.println("当前内存使用量: " + memory.get("used"));
		System.out.println("当前内存剩余量: " + memory.get("free"));
		System.out.println("--------------------------------------------");
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	

}
