package jisheng.服务器;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class MyDefineServerHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		String str="<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"<head>\r\n" + 
				"    <meta charset=\"UTF-8\">\r\n" + 
				"    <title>netty服务器</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"    <h1 >netty</h1>\r\n" + 
				"<div style=\"height: 400px;width:300px;margin:0px auto;\">\r\n" + 
				"    <form>\r\n" + 
				"        姓名<input type=\"text\"/><br>\r\n" + 
				"        性别<input type=\"radio\" name=\"sex\" >男 <input type=\"radio\" name=\"sex\" >女<br>\r\n" + 
				"        电话<input type=\"text\"><br>\r\n" + 
				"        <input type=\"button\" value=\"提交\">\r\n" + 
				"    </form>\r\n" + 
				"\r\n" + 
				"</div>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>";
		File file=new File("C:\\Users\\ASUS\\Desktop\\1.jpg");
		byte[] byts=new byte[1024];
		try {
			FileInputStream fis=new FileInputStream(file);
			while (fis.read(byts)!=-1) {
				fis.read(byts);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		if (msg instanceof HttpRequest) {
			HttpRequest req = (HttpRequest) msg;
			System.out.println(req);
			//
			// if (HttpHeaderUtil.is100ContinueExpected(req)) {
			// ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
			// }
			// boolean keepAlive = HttpHeaderUtil.isKeepAlive(req);
			FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(str.getBytes()));
			response.headers().set(CONTENT_TYPE, "text/html");
			response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

			// if (!keepAlive) {
			// ctx.write(response).addListener(ChannelFutureListener.CLOSE);
			// } else {
//			response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
			ctx.write(response);
			// }
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}