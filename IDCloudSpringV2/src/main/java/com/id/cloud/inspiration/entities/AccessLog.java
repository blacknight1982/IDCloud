package com.id.cloud.inspiration.entities;

import java.util.Calendar;

public class AccessLog {
	
	private int accessid;
	
	private Calendar time;
	
	private String ip;
	
	private String userAgent;
	
	private String accessModule;
	
	private String user;
	
	public AccessLog(String ip, String userAgent, String accessModule, String user){
		this.ip = ip;
		this.userAgent = userAgent;
		this.accessModule = accessModule;
		this.user = user;
		time = Calendar.getInstance();
	}

	public int getAccessid() {
		return accessid;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAccessModule() {
		return accessModule;
	}

	public void setAccessModule(String accessModule) {
		this.accessModule = accessModule;
	}

	public void setAccessid(int accessid) {
		this.accessid = accessid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
