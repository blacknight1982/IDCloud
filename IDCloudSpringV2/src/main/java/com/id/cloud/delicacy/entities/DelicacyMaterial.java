package com.id.cloud.delicacy.entities;


public class DelicacyMaterial {

	/**
	 * Primary key of delicacy material
	 */
	private int materialID;
	
	/**
	 * Name of delicacy raw material
	 */
	private String materialName;
	
	/**
	 * Description of delicacy raw material
	 */
	private String materialDescription;
	
	/**
	 * Type of delicacy raw material
	 */
	private String materialType;
	
	public int getMaterialID() {
		return materialID;
	}

	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
}
