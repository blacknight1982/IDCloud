package com.id.cloud.delicacy.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.delicacy.entities.DelicacyMedia;

public class DelicacyMediaMapper implements RowMapper<DelicacyMedia>{

	@Override
	public DelicacyMedia mapRow(ResultSet rs, int rowNum) throws SQLException {
		DelicacyMedia delicacyMedia = new DelicacyMedia();
		delicacyMedia.setMediaID(rs.getInt("MEDIA_ID"));
		delicacyMedia.setMediaName(rs.getString("MEDIA_NAME"));
		delicacyMedia.setMediaURL(rs.getString("MEDIA_URL"));
		delicacyMedia.setMediaType(rs.getString("MEDIA_TYPE"));
		return delicacyMedia;
	}
}
