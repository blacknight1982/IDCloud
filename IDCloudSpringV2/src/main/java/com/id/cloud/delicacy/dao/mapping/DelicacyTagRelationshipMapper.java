package com.id.cloud.delicacy.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.delicacy.entities.DelicacyTagRelationship;

public class DelicacyTagRelationshipMapper implements RowMapper<DelicacyTagRelationship>{

	@Override
	public DelicacyTagRelationship mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		DelicacyTagRelationship delicacyTagRelationship = new DelicacyTagRelationship();
		delicacyTagRelationship.setDelicacyID(rs.getInt("DELICACY_ID"));
		delicacyTagRelationship.setRelationID(rs.getInt("RELATION_ID"));
		delicacyTagRelationship.setTagID(rs.getInt("TAG_ID"));
		return delicacyTagRelationship;
	}
}
