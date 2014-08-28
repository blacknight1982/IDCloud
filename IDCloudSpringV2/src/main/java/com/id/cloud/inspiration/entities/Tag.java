package com.id.cloud.inspiration.entities;


public class Tag {
	
	public static void main(String[] args) {
		//Tag tag = new Tag("tag","#00f000");
		System.out.println(new Tag("tag","#00f000").getComplementaryColorInHex());
	}
	
	/**
	 * Primary key of tag
	 */
	private int tagID;
	
	/**
	 * Tag name
	 */
	private String name;
	
	/**
	 * Color of tag which is used for presenting
	 */
	private String color;
	

	public Tag(String name, String color){
		this.name = name;
		this.color = color;
	}

	public int getTagID() {
		return tagID;
	}

	public void setTagID(int tagID) {
		this.tagID = tagID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public String getComplementaryColorInHex() {
		int intColor = Integer.parseInt(color.substring(1), 16); 
		int colorB=255-intColor%256;
		int colorG=255-((intColor>>8)%256);
		int colorR=255-((intColor>>16)%256);
		int complementaryColor = (colorR<<16)+(colorG<<8)+(colorB);
		return String.format("#%06x", complementaryColor);	
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (!(obj instanceof Tag)) {  
            return false;  
        } 
		return (((Tag)obj).getTagID() == tagID &&((Tag)obj).getName().equals(name)&&((Tag)obj).getColor().equals(color));
	}
}
