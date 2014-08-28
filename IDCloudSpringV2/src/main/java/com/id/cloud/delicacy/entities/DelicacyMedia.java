package com.id.cloud.delicacy.entities;

public class DelicacyMedia {
	
	/**
	 * Primary key for media
	 */
	private int mediaID;
	
	/**
	 * Name of media
	 */
	private String mediaName;
	
	/**
	 * URL of media resource
	 */
	private String mediaURL;
	
	/**
	 * extension name of media
	 */
	private String mediaType;

	public int getMediaID() {
		return mediaID;
	}

	public void setMediaID(int mediaID) {
		this.mediaID = mediaID;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaURL() {
		return mediaURL;
	}

	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
}
