package com.id.cloud.inspiration.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewAccountForm extends AccountForm {
	
	@NotNull(message = "Invitation Code is required")
	@Size(min=1, message="Invitation Code is required")
	private String idcloud_invitation_code;
	
	public String getIdcloud_invitation_code() {
		return idcloud_invitation_code;
	}

	public void setIdcloud_invitation_code(String idcloud_invitation_code) {
		this.idcloud_invitation_code = idcloud_invitation_code;
	}
}
