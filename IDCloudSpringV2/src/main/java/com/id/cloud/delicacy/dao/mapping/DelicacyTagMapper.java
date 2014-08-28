package com.id.cloud.delicacy.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.delicacy.entities.DelicacyTag;

public class DelicacyTagMapper implements RowMapper<DelicacyTag>{

	@Override
	public DelicacyTag mapRow(ResultSet rs, int rowNum) throws SQLException {
		DelicacyTag delicacyTag = new DelicacyTag();
		delicacyTag.setColor(rs.getInt("TAG_COLOR"));
		delicacyTag.setTagID(rs.getInt("TAG_ID"));
		delicacyTag.setTagName(rs.getString("TAG_NAME"));
		return delicacyTag;
	}
}
