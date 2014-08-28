package com.id.cloud.delicacy.dao;

import com.id.cloud.delicacy.entities.Delicacy;

public interface DelicacyDao {
	
	public int create(Delicacy delicacy);
	
	public void update(Delicacy delicacy);
	
	public Delicacy getByPrimaryKey(int id);
	
	public void deleteByPrimaryKey(int id);
	
	public void deleteAll();
}
