package com.id.cloud.inspiration.entities;

import javax.validation.constraints.Size;

public class LoginForm {
	@Size(min=1, message="Username is required")
	private String idcloud_username;
	
	@Size(min=1, message="Password is required")
	private String idcloud_password;

	public String getIdcloud_username() {
		return idcloud_username;
	}

	public void setIdcloud_username(String idcloud_username) {
		this.idcloud_username = idcloud_username;
	}

	public String getIdcloud_password() {
		return idcloud_password;
	}

	public void setIdcloud_password(String idcloud_password) {
		this.idcloud_password = idcloud_password;
	}
	
}
