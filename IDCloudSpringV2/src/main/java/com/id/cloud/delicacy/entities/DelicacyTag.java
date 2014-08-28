package com.id.cloud.delicacy.entities;

public class DelicacyTag {
	
	/**
	 * Primary key of delicacy tags
	 */
	private int tagID;
	
	/**
	 * Name field of delicacy tag
	 */
	private String tagName;
	
	/**
	 * Color value for the delicacy tag
	 */
	private int color;

	public int getTagID() {
		return tagID;
	}

	public void setTagID(int tagID) {
		this.tagID = tagID;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
