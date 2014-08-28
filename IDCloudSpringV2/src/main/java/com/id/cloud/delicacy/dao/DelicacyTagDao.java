package com.id.cloud.delicacy.dao;

import com.id.cloud.delicacy.entities.DelicacyTag;

public interface DelicacyTagDao {

	public int create(DelicacyTag delicacyTag);

	public void update(DelicacyTag delicacyTag);

	public DelicacyTag getByPrimaryKey(int id);

	public void deleteByPrimaryKey(int id);

	public void deleteAll();
}
