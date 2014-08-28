package com.id.cloud.delicacy.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.delicacy.entities.Delicacy;

public class DelicacyMapper implements RowMapper<Delicacy>{

	@Override
	public Delicacy mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Delicacy delicacy = new Delicacy();
		delicacy.setDelicacyID(rs.getInt("DELICACY_ID"));
		delicacy.setDelicacyName(rs.getString("DELICACY_NAME"));
		delicacy.setDelicacyHighlight(rs.getString("DELICACY_HIGHLIGHT"));
		delicacy.setDelicacyStory(rs.getString("DELICACY_STORY"));
		return delicacy;
	}
}
