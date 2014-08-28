package com.id.cloud.delicacy.dao;

import com.id.cloud.delicacy.entities.DelicacyTagRelationship;

public interface DelicacyTagRelationshipDao {

	public int create(DelicacyTagRelationship delicacyTagRelationship);

	public void update(DelicacyTagRelationship delicacyTagRelationship);

	public DelicacyTagRelationship getByPrimaryKey(int id);

	public void deleteByPrimaryKey(int id);

	public void deleteAll();
}
