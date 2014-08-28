package com.id.cloud.delicacy.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="IDUSER")
public class IDUser implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 6115169301047581208L;
	
	@EmbeddedId
	private IDUserPK idUserPK;
    
	@Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
    private String lastName;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@ManyToMany(targetEntity=com.id.cloud.delicacy.entities.IDRole.class,
			cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="ID_USER_ROLE", 
		joinColumns={@JoinColumn(name="GROUP_ID"),@JoinColumn(name="USER_NAME")},
		inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
	private Set<IDRole> roles = new HashSet<IDRole>();
	
	public IDUser(){
		super();
		idUserPK = new IDUserPK();
	}
	
	public IDUser(Integer groupID,String userName, String firstName, String lastName,String email, String password){
		this.idUserPK.setGroupID(groupID);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.idUserPK.setUserName(userName);
		this.setEmail(email);
		this.setPassword(password);
	}
    
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<IDRole> getRoles() {
		return roles;
	}
	public void setRoles(Set<IDRole> role) {
		this.roles = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public IDUserPK getIdUserPK() {
		return idUserPK;
	}

	public void setIdUserPK(IDUserPK idUserPK) {
		this.idUserPK = idUserPK;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return idUserPK.getUserName();
	}

}
