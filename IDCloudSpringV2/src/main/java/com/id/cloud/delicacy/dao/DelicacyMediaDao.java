package com.id.cloud.delicacy.dao;

import com.id.cloud.delicacy.entities.DelicacyMedia;

public interface DelicacyMediaDao {
	
	public int create(DelicacyMedia delicacyMedia);

	public void update(DelicacyMedia delicacyMedia);

	public DelicacyMedia getByPrimaryKey(int id);

	public void deleteByPrimaryKey(int id);

	public void deleteAll();
}
