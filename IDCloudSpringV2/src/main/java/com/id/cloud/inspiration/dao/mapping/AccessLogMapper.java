package com.id.cloud.inspiration.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.inspiration.entities.AccessLog;

public class AccessLogMapper implements RowMapper<AccessLog>{

	@Override
	public AccessLog mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccessLog accessLog = new AccessLog(rs.getString("IP"),rs.getString("USER_AGENT"),rs.getString("ACCESS_MODULE"),rs.getString("USER"));
		Calendar accessTime = Calendar.getInstance();
		accessTime.setTime(rs.getTimestamp("ACCESS_TIME"));
		accessLog.setTime(accessTime);
		return accessLog;
	}
}
