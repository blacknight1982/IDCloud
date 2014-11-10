package com.id.cloud.inspiration.utility.imageupload;

import java.util.Calendar;

public class RestImage {

	private String name;
	private String title;
	private String caption;
	private String hash;
	private String deletehash;
	private Calendar datetime = Calendar.getInstance();
	private String type;
	private boolean animated;
	private int width;
	private int height;
	private int size;
	private int views;
	private int bandwidth;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getDeletehash() {
		return deletehash;
	}
	public void setDeletehash(String deletehash) {
		this.deletehash = deletehash;
	}
	public Calendar getDatetime() {
		return datetime;
	}
	public void setDatetime(Calendar datetime) {
		this.datetime = datetime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAnimated() {
		return animated;
	}
	public void setAnimated(boolean animated) {
		this.animated = animated;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	
}
