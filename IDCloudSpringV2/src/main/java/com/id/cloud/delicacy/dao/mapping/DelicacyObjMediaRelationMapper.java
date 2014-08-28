package com.id.cloud.delicacy.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.delicacy.entities.DelicacyObjMediaRelation;
import com.id.cloud.delicacy.entities.DelicacyObjectType;

public class DelicacyObjMediaRelationMapper implements RowMapper<DelicacyObjMediaRelation>{

	@Override
	public DelicacyObjMediaRelation mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		DelicacyObjMediaRelation delicacyObjMediaRelation = new DelicacyObjMediaRelation();
		delicacyObjMediaRelation.setRelationID(rs.getInt("RELATION_ID"));
		delicacyObjMediaRelation.setMediaID(rs.getInt("MEDIA_ID"));
		delicacyObjMediaRelation.setObjectID(rs.getInt("OBJ_ID"));
		delicacyObjMediaRelation.setDelicacyObjType(DelicacyObjectType.getDelicacyObjectType(rs.getInt("DELICACY_OBJ_TYPE")));
		return delicacyObjMediaRelation;
	}

}
