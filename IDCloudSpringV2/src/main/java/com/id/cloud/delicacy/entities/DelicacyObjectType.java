package com.id.cloud.delicacy.entities;

public enum DelicacyObjectType {
	
	DELICACY(1),
	DELICACYMATERIAL(2),
	DELICACYINGREDIENT(3),
	DELICACYSTEP(4),
	DELICACYCOOKTOOL(5);
	
	private int value;
	
	private static DelicacyObjectType[] values = DelicacyObjectType.values();
	
	public static DelicacyObjectType getDelicacyObjectType(int i) {
	    return values[i - 1];
	}
	
	private DelicacyObjectType(int  value){
		this.setValue(value);
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
