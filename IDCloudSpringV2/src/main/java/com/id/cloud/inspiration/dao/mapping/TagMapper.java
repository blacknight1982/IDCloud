package com.id.cloud.inspiration.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.inspiration.entities.Tag;

public class TagMapper implements RowMapper<Tag>{

	@Override
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tag tag = new Tag(rs.getString("TAG_NAME"),rs.getString("TAG_COLOR"));
		tag.setTagID(rs.getInt("TAG_ID"));
		return tag;
	}
}
