package jisheng.heartbeat;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

import io.netty.channel.ChannelFuture;

public class GetCpuMemoryParameters implements Runnable {
	InetAddress addr;
	ChannelFuture future;

	public GetCpuMemoryParameters(ChannelFuture future) {
		this.future = future;
	}

	@Override
	public void run() {
		try {
			addr=InetAddress.getLocalHost();
			ClientRequest info = new ClientRequest();
			// ip
			info.setIp(addr.getHostAddress());
			Sigar sigar = new Sigar();
			// cpu prec
			CpuPerc cpuPerc = sigar.getCpuPerc();
			Map<String, Object> cpuPercMap = new HashMap<String, Object>();
			cpuPercMap.put("combined", cpuPerc.getCombined());
			cpuPercMap.put("user", cpuPerc.getUser());
			cpuPercMap.put("sys", cpuPerc.getSys());
			cpuPercMap.put("wait", cpuPerc.getWait());
			cpuPercMap.put("idle", cpuPerc.getIdle());
			// memory
			Mem mem = sigar.getMem();
			Map<String, Object> memoryMap = new HashMap<String, Object>();
			memoryMap.put("total", mem.getTotal() / 1024L);
			memoryMap.put("used", mem.getUsed() / 1024L);
			memoryMap.put("free", mem.getFree() / 1024L);
			info.setCpu(cpuPercMap);
			info.setMemory(memoryMap);
			future.channel().writeAndFlush(info);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
