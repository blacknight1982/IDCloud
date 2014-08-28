package com.id.cloud.delicacy.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.delicacy.entities.DelicacyMaterial;


public class DelicacyMaterialMapper implements RowMapper<DelicacyMaterial>{

	@Override
	public DelicacyMaterial mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		DelicacyMaterial delicacyMaterial = new DelicacyMaterial();
		delicacyMaterial.setMaterialID(rs.getInt("MATERIAL_ID"));
		delicacyMaterial.setMaterialName(rs.getString("MATERIAL_NAME"));
		delicacyMaterial.setMaterialDescription(rs.getString("MATERIAL_DES"));
		delicacyMaterial.setMaterialType(rs.getString("MATERIAL_TYPE"));
		return null;
	}
}
