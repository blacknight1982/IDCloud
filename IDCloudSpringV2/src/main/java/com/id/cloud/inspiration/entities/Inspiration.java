package com.id.cloud.inspiration.entities;

import java.util.Calendar;
import java.util.List;

public class Inspiration {
	
	/**
	 * Primary Key for Inspiration
	 */
	private int inspirationID;
	
	/**
	 * Inspiration Article Title
	 */
	private String title;
	
	/**
	 * Inspiration post time
	 */
	private Calendar postTime;
	
	/**
	 * Inspiration page load relative location
	 */
	private String mainPageLocation;
	
	/**
	 * Inspiration author
	 */
	private String author;
	
	
	/**
	 * Inspiration author nickname.
	 * Non-persistent attribute
	 * Normally, it's null. Only accessible through JSP page after value fulfilled. 
	 */
	private String authorNickname;
	
	public String getAuthorNickname() {
		return authorNickname;
	}

	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}

	/**
	 * Inspiration associated Tags.
	 * Non-persistent attribute
	 * Normally, it's empty. Only accessible through JSP page after value fulfilled. 
	 */
	private List<Tag> tags;

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public int getInspirationID() {
		return inspirationID;
	}

	public void setInspirationID(int inspirationID) {
		this.inspirationID = inspirationID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getPostTime() {
		return postTime;
	}

	public void setPostTime(Calendar postTime) {
		this.postTime = postTime;
	}

	public String getMainPageLocation() {
		return mainPageLocation;
	}

	public void setMainPageLocation(String mainPageLocation) {
		this.mainPageLocation = mainPageLocation;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
