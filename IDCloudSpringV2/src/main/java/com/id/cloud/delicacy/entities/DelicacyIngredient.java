package com.id.cloud.delicacy.entities;


public class DelicacyIngredient {
		
	/**
	 * Primary key of delicacy ingredient
	 */
	private int ingredientID;
	
	/**
	 * Name of delicacy ingredient
	 */
	private String ingredientName;
	
	/**
	 * Foreign key to delicacy
	 */
	private int delicacyID;
	
	/**
	 * Foreign key to delicacy material
	 */
	private int delicacyMaterialID;
	
	/**
	 * Unit of delicacy ingredient
	 */
	private String ingredientUnit;
	
	/**
	 * How many ingredient by unit needed for making the delicacy 
	 */
	private double ingredientVolume;

	public int getIngredientID() {
		return ingredientID;
	}

	public void setIngredientID(int ingredientID) {
		this.ingredientID = ingredientID;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public int getDelicacyID() {
		return delicacyID;
	}

	public void setDelicacyID(int delicacyID) {
		this.delicacyID = delicacyID;
	}

	public int getDelicacyMaterialID() {
		return delicacyMaterialID;
	}

	public void setDelicacyMaterialID(int delicacyMaterialID) {
		this.delicacyMaterialID = delicacyMaterialID;
	}

	public String getIngredientUnit() {
		return ingredientUnit;
	}

	public void setIngredientUnit(String ingredientUnit) {
		this.ingredientUnit = ingredientUnit;
	}

	public double getIngredientVolume() {
		return ingredientVolume;
	}

	public void setIngredientVolume(double ingredientVolume) {
		this.ingredientVolume = ingredientVolume;
	}
	
}
