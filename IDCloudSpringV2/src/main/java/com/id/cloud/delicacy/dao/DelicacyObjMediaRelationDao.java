package com.id.cloud.delicacy.dao;

import com.id.cloud.delicacy.entities.DelicacyObjMediaRelation;

public interface DelicacyObjMediaRelationDao {

	public int create(DelicacyObjMediaRelation delicacyObjMediaRelation);

	public void update(DelicacyObjMediaRelation delicacyObjMediaRelation);

	public DelicacyObjMediaRelation getByPrimaryKey(int id);

	public void deleteByPrimaryKey(int id);

	public void deleteAll();
}
