package com.id.cloud.inspiration.entities;

public class InspirationM2MTag {
	
	/**
	 * Primary key of inspiration tag Many-to-Many relationship 
	 */
	private int relationID;
	
	/**
	 * Foreign key of inspiration ID
	 */
	private int inspirationID;
	
	/**
	 * Foreign key of tag ID
	 */
	private int tagID;
	
	public InspirationM2MTag(int inspirationID, int tagID){
		this.inspirationID = inspirationID;
		this.tagID = tagID;
	}
	
	public int getRelationID() {
		return relationID;
	}
	public void setRelationID(int relationID) {
		this.relationID = relationID;
	}
	public int getInspirationID() {
		return inspirationID;
	}
	public void setInspirationID(int inspirationID) {
		this.inspirationID = inspirationID;
	}
	public int getTagID() {
		return tagID;
	}
	public void setTagID(int tagID) {
		this.tagID = tagID;
	}
	
	
}
