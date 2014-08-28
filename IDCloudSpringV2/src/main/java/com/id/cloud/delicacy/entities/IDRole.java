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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="IDROLE")
public class IDRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7103352427598510895L;

	@Id
    @Column(name = "ROLE_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer roleID;

	@Column(name = "ROLE_NAME", nullable = false)
    private String roleName;

	@Column(name = "ROLE_DESC", nullable = false)
    private String roleDescription;
    
	@ManyToMany(targetEntity = com.id.cloud.delicacy.entities.IDUser.class,
			cascade={CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy = "roles")
    private Set<IDUser> users = new HashSet<IDUser>();
	
	@ManyToMany(targetEntity=com.id.cloud.delicacy.entities.IDAction.class,
			cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="ID_ROLE_ACTION", 
		joinColumns={@JoinColumn(name="ROLE_ID")},
		inverseJoinColumns={@JoinColumn(name="ACTION_ID")})
	private Set<IDAction> actions = new HashSet<IDAction>();
	
	public IDRole(){
		super();
	}
	
	public IDRole(String roleName, String roleDescription){
		this.setRoleID(roleID);
		this.setRoleName(roleName);
		this.setRoleDescription(roleDescription);
	}

	public Integer getRoleID() {
		return roleID;
	}

	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Set<IDUser> getUsers() {
		return users;
	}

	public void setUsers(Set<IDUser> users) {
		this.users = users;
	}

	public Set<IDAction> getActions() {
		return actions;
	}

	public void setActions(Set<IDAction> action) {
		this.actions = action;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return roleName;
	}
}
