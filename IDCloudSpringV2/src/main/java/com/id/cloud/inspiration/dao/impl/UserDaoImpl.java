package com.id.cloud.inspiration.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.id.cloud.inspiration.dao.UserDao;
import com.id.cloud.inspiration.dao.mapping.UserMapper;
import com.id.cloud.inspiration.entities.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
/*	@Override
	public int create(final User user) {
		if(user == null) return 0;
		final String SQL = "insert into USERS (USERNAME, PASSWORD, ENABLED, NICKNAME) values (?, ?, ?, ?)";
		jdbcTemplate.update(SQL,user.getUsername(),user.getPassword(), user.isEnabled(), user.getNickname());
		return 0;
	}*/
	
	@Override
	public int create(final User user) {
		if(user == null) return 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String SQL = "insert into USERS (USERNAME, PASSWORD, ENABLED, NICKNAME) values (?, ?, ?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"ID"});
	            ps.setString(1, user.getUsername());
	            ps.setString(2, user.getPassword());
	            ps.setBoolean(3,user.isEnabled());
	            ps.setString(4, user.getNickname());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(User user) {
		if(user == null) return ;
		final String SQL = "UPDATE USERS set PASSWORD=?, ENABLED=?, NICKNAME=? where USERNAME=?";
		jdbcTemplate.update(SQL,user.getPassword(), user.isEnabled(), user.getNickname(),user.getUsername());
	}

	@Override
	public User findByUsername(String username) {
		String SQL = "select * from users where username = ? ";
		return jdbcTemplate.queryForObject(SQL,new UserMapper(), username);
	}
	
	@Override
	public User findByPrimaryKey(int id) {
		String SQL = "select * from users where id = ? ";
		return jdbcTemplate.queryForObject(SQL,new UserMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(String username) {
		String SQL = "delete from USERS where username = ? ";
		jdbcTemplate.update(SQL, username);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from USERS";
		jdbcTemplate.update(SQL);
	}

	@Override
	public void deleteByUserIDs(String[] userIDs) {
		StringBuilder sqlBuilder = new StringBuilder("DELETE FROM USERS");
		if(userIDs != null){
			if(userIDs.length>0){
				sqlBuilder.append(" WHERE USERNAME in (?");
				for (int i=1;i<userIDs.length;i++){
					sqlBuilder.append(",?");
				}
				sqlBuilder.append(")");
			}
			else return;
		}
		jdbcTemplate.update(sqlBuilder.toString(), (Object[])userIDs);
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM USERS";
		List<User> user = jdbcTemplate.query(sql, new UserMapper());
		return user;
	}

	@Override
	public List<User> findByUserIDs(String[] userIDs) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM USERS");
		if(userIDs != null){
			if(userIDs.length>0) 
			{
				sqlBuilder.append(" WHERE USERNAME in (?");
				for (int i=1;i<userIDs.length;i++){
					sqlBuilder.append(",?");
				}
				sqlBuilder.append(")");
			}
			
			else return new ArrayList<User>(); 
		}
		List<User> users = jdbcTemplate.query(sqlBuilder.toString(), userIDs ,new UserMapper());
		return users;
	}
}
