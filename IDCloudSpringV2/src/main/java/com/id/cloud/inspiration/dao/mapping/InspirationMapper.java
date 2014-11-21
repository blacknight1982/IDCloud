package com.id.cloud.inspiration.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.inspiration.entities.Inspiration;

public class InspirationMapper implements RowMapper<Inspiration>{

	@Override
	public Inspiration mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Inspiration inspiration = new Inspiration();
		Calendar postTime = Calendar.getInstance();
		inspiration.setInspirationID(rs.getInt("INSPIRATION_ID"));
		inspiration.setUuid(rs.getString("INSPIRATION_UUID"));
		inspiration.setTitle(rs.getString("INSPIRATION_TITLE"));
		inspiration.setMainPageLocation(rs.getString("INSPIRATION_LOCATION"));
		postTime.setTime(rs.getTimestamp("INSPIRATION_POSTTIME"));
		inspiration.setPostTime(postTime);
		inspiration.setAuthor(rs.getString("INSPIRATION_AUTHOR"));
		inspiration.setAuthLevel(rs.getInt("AUTH_LEVEL"));
		inspiration.setBriefing(rs.getString("INSPIRATION_BRIEFING"));
		return inspiration;
	}
}
