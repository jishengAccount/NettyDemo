package jisheng.heartbeat;

import java.io.Serializable;
import java.util.Map;


public class ClientRequest implements Serializable{
	private String ip;
	private Map<String, Object> cpu;
	private Map<String, Object> memory;
	public Map<String, Object> getCpu() {
		return cpu;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setCpu(Map<String, Object> cpu) {
		this.cpu = cpu;
	}
	public Map<String, Object> getMemory() {
		return memory;
	}
	public void setMemory(Map<String, Object> memory) {
		this.memory = memory;
	}
	
	

}
