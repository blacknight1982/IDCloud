package com.id.cloud.delicacy.entities;


public class DelicacyObjMediaRelation{

	/**
	 * Primary key for each delicacy object media relationship
	 */
	private int relationID;
	
	/**
	 * Foreign key to delicacy object
	 */
	private int objectID;
	
	/**
	 * Foreign key to media
	 */
	private int mediaID;
	
	/**
	 * Type of delicacy object
	 */
	private DelicacyObjectType delicacyObjType;

	public DelicacyObjectType getDelicacyObjType() {
		return delicacyObjType;
	}

	public void setDelicacyObjType(DelicacyObjectType delicacyObjType) {
		this.delicacyObjType = delicacyObjType;
	}

	public int getRelationID() {
		return relationID;
	}

	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}

	public int getObjectID() {
		return objectID;
	}

	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	public int getMediaID() {
		return mediaID;
	}

	public void setMediaID(int mediaID) {
		this.mediaID = mediaID;
	}
}
