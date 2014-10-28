package com.id.cloud.inspiration.entities;

import javax.validation.constraints.Size;

public class AccountForm extends LoginForm {
	
	@Size(min=1, message="Username is required")
	private String idcloud_nickname;
	
	@Size(min=1, message="Confirm Password is required")
	private String idcloud_confirm_password;

	public String getIdcloud_nickname() {
		return idcloud_nickname;
	}

	public void setIdcloud_nickname(String idcloud_nickname) {
		this.idcloud_nickname = idcloud_nickname;
	}

	public String getIdcloud_confirm_password() {
		return idcloud_confirm_password;
	}

	public void setIdcloud_confirm_password(String idcloud_confirm_password) {
		this.idcloud_confirm_password = idcloud_confirm_password;
	}
}
