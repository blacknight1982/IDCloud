package com.id.cloud.delicacy.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="IDACTION")
public class IDAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7654887716041674172L;
	
	@Id
    @Column(name = "ACTION_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer actionID;

	@Column(name = "ACTION_NAME", nullable = false)
    private String actionName;

	@Column(name = "ACTION_DESC", nullable = false)
    private String actionDescription;
    
	@ManyToMany(targetEntity = com.id.cloud.delicacy.entities.IDRole.class,
			cascade={CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy = "actions")
    private Set<IDRole> roles = new HashSet<IDRole>();

	public Integer getActionID() {
		return actionID;
	}

	public void setActionID(Integer actionID) {
		this.actionID = actionID;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	public Set<IDRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<IDRole> role) {
		this.roles = role;
	}

}
