package com.id.cloud.delicacy.entities;

public class DelicacyTagRelationship {

	/**
	 * Primary key of delicacy-tag relationship 
	 */
	private int relationID;
	
	/**
	 * Foreign key to delicacy ID
	 */
	private int delicacyID;
	
	/**
	 * Foreign key to delicacy tag ID
	 */
	private int tagID;

	public int getRelationID() {
		return relationID;
	}

	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}

	public int getDelicacyID() {
		return delicacyID;
	}

	public void setDelicacyID(int delicacyID) {
		this.delicacyID = delicacyID;
	}

	public int getTagID() {
		return tagID;
	}

	public void setTagID(int tagID) {
		this.tagID = tagID;
	}
	
	
}
