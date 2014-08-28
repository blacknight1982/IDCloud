package com.id.cloud.delicacy.entities;



public class Delicacy{

	/**
	 * Primary key for each delicacy
	 */
	private int delicacyID;
	
	/**
	 * Name of delicacy
	 */
	private String delicacyName;
	
	/**
	 * Highlight of delicacy
	 */
	private String delicacyHighlight;
	
	/**
	 * Story of delicacy
	 */
	private String delicacyStory;
	

	public int getDelicacyID() {
		return delicacyID;
	}

	public void setDelicacyID(int delicacyID) {
		this.delicacyID = delicacyID;
	}

	public String getDelicacyName() {
		return delicacyName;
	}

	public void setDelicacyName(String delicacyName) {
		this.delicacyName = delicacyName;
	}

	public String getDelicacyHighlight() {
		return delicacyHighlight;
	}

	public void setDelicacyHighlight(String delicacyHighlight) {
		this.delicacyHighlight = delicacyHighlight;
	}

	public String getDelicacyStory() {
		return delicacyStory;
	}

	public void setDelicacyStory(String delicacyStory) {
		this.delicacyStory = delicacyStory;
	}

}
