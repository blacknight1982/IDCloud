package com.id.cloud.inspiration.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.inspiration.entities.UserRole;

public class UserRoleMapper implements RowMapper<UserRole>{
	
	@Override
	public UserRole mapRow(ResultSet rs, int num) throws SQLException {
		UserRole userRole = new UserRole(rs.getInt("id"), rs.getString("username"),rs.getString("role"));
		return userRole;
	}
}
