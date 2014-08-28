package com.id.cloud.delicacy.entities;

public class DelicacyStep {

	/**
	 * Primary key for delicacy step
	 */
	private int stepID;
	
	/**
	 * Number for step order
	 */
	private int stepNum;
	
	/**
	 * Name of delicacy step
	 */
	private String stepName;
	
	/**
	 * Description of delicacy step
	 */
	private String stepDescription;
	
	/**
	 * Estimated time of each step making delicacy in seconds
	 */
	private int stepTime;
	
	/**
	 * Foreign key to delicacy
	 */
	private int delicacyID;

	public int getStepID() {
		return stepID;
	}

	public void setStepID(int stepID) {
		this.stepID = stepID;
	}

	public int getStepNum() {
		return stepNum;
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getStepDescription() {
		return stepDescription;
	}

	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}

	public int getStepTime() {
		return stepTime;
	}

	public void setStepTime(int stepTime) {
		this.stepTime = stepTime;
	}

	public int getDelicacyID() {
		return delicacyID;
	}

	public void setDelicacyID(int delicacyID) {
		this.delicacyID = delicacyID;
	}	
}
