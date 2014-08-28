package com.id.cloud.delicacy.dao;

import com.id.cloud.delicacy.entities.DelicacyIngredient;

public interface DelicacyIngredientDao {
	
	public int create(DelicacyIngredient delicacyIngredient);

	public void update(DelicacyIngredient delicacyIngredient);

	public DelicacyIngredient getByPrimaryKey(int id);

	public void deleteByPrimaryKey(int id);

	public void deleteAll();
}
