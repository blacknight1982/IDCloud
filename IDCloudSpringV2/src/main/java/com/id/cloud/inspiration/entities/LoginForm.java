package com.id.cloud.inspiration.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Handle end user login information.
 * Non-persist object. For view layer only.
 * @author John
 *
 */
public class LoginForm {
	
	@NotNull
	@Size(min=1, message="Username is required")
	@Pattern(regexp="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message="Email address does not match the pattern")
	private String idcloud_username;
	
	@NotNull
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
