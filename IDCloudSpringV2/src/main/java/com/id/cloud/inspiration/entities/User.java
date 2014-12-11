package com.id.cloud.inspiration.entities;

import java.util.List;

public class User {
	
	private int id;

	private String username;
	
	private String password;
	
	private boolean enabled;
	
	private String nickname;
	
	private List<UserRole> userRoles;
	
	public User(){
		
	}
	
	public User(String username, String password, boolean enabled, String nickname){
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.nickname = nickname;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> roles) {
		this.userRoles = roles;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
