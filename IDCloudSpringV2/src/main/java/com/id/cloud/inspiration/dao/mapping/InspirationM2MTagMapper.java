package com.id.cloud.inspiration.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.inspiration.entities.InspirationM2MTag;

public class InspirationM2MTagMapper implements RowMapper<InspirationM2MTag>{

	@Override
	public InspirationM2MTag mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		InspirationM2MTag inspirationM2MTag = new InspirationM2MTag(rs.getInt("INSPIRATION_ID"),rs.getInt("TAG_ID"));
		inspirationM2MTag.setRelationID(rs.getInt("RELATION_ID"));
		return inspirationM2MTag;
	}

}
