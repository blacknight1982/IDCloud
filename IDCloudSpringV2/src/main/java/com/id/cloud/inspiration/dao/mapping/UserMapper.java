package com.id.cloud.inspiration.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.inspiration.entities.User;

public class UserMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int num) throws SQLException {
		User user = new User(rs.getString("username"),rs.getString("password"),rs.getBoolean("enabled"),rs.getString("nickname"));
		return user;
	}

}
