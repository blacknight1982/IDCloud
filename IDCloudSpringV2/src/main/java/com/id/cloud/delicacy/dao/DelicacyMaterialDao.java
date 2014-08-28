package com.id.cloud.delicacy.dao;

import com.id.cloud.delicacy.entities.DelicacyMaterial;

public interface DelicacyMaterialDao {
	
	public int create(DelicacyMaterial delicacyMaterial);

	public void update(DelicacyMaterial delicacyMaterial);

	public DelicacyMaterial getByPrimaryKey(int id);

	public void deleteByPrimaryKey(int id);

	public void deleteAll();
}
