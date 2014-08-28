package com.id.cloud.delicacy.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IDUserPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8972074772957716020L;
	
	@Column(name = "GROUP_ID", nullable = false)
	private Integer groupID;
	
	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	public IDUserPK(){
		super();
	}
	
	public IDUserPK(Integer groupID){
		this.groupID = groupID;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}	

	@Override
	public int hashCode() {
		return groupID * 100000 + userName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return (this == obj)|| 
				((obj != null) && 
				(obj.getClass() == this.getClass()) && 
				((IDUserPK)obj).getGroupID().equals(this.getGroupID()) &&
				((IDUserPK)obj).getUserName().equals(this.getUserName()));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return groupID + "_" + userName;
	}
}