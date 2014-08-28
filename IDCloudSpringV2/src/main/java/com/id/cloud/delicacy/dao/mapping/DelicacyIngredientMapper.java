package com.id.cloud.delicacy.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.delicacy.entities.DelicacyIngredient;

public class DelicacyIngredientMapper implements RowMapper<DelicacyIngredient>{

	@Override
	public DelicacyIngredient mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		DelicacyIngredient delicacyIngredient = new DelicacyIngredient();
		delicacyIngredient.setDelicacyID(rs.getInt("DELICACY_ID"));
		delicacyIngredient.setDelicacyMaterialID(rs.getInt("DELICACY_MAT_ID"));
		delicacyIngredient.setIngredientID(rs.getInt("INGREDIENT_ID"));
		delicacyIngredient.setIngredientName(rs.getString("INGREDIENT_NAME"));
		delicacyIngredient.setIngredientUnit(rs.getString("INGREDIENT_UNIT"));
		delicacyIngredient.setIngredientVolume(rs.getDouble(""));
		return delicacyIngredient;
	}
}
