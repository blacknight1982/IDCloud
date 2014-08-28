package com.id.cloud.delicacy.dao;

import com.id.cloud.delicacy.entities.DelicacyStep;

public interface DelicacyStepDao {

	public int create(DelicacyStep delicacyStep);

	public void update(DelicacyStep delicacyStep);

	public DelicacyStep getByPrimaryKey(int id);

	public void deleteByPrimaryKey(int id);

	public void deleteAll();
}
