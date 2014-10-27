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

import com.id.cloud.inspiration.dao.UserRoleDao;
import com.id.cloud.inspiration.dao.mapping.UserRoleMapper;
import com.id.cloud.inspiration.entities.UserRole;

@Repository
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final UserRole userRole) {
		if(userRole == null) return 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String SQL = "insert into USER_ROLES (USERNAME, ROLE) values (?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"ID"});
	            ps.setString(1, userRole.getUsername());
	            ps.setString(2, userRole.getRole());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(UserRole userRole) {
		if(userRole == null) return ;
		final String SQL = "UPDATE USER_ROLES set ROLE=?, USERNAME=? where ID=?";
		jdbcTemplate.update(SQL,userRole.getRole(),userRole.getId());
	}

	@Override
	public UserRole findByPrimaryKey(int id) {
		String SQL = "select * from USER_ROLES where id = ? ";
		return jdbcTemplate.queryForObject(SQL,new UserRoleMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from USER_ROLES where id = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from USER_ROLES";
		jdbcTemplate.update(SQL);
	}

	@Override
	public void deleteByUserIDs(String[] userIDs) {
		StringBuilder sqlBuilder = new StringBuilder("DELETE FROM USER_ROLES");
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
	public List<UserRole> findAll() {
		String sql = "SELECT * FROM USER_ROLES";
		List<UserRole> userRole = jdbcTemplate.query(sql, new UserRoleMapper());
		return userRole;
	}

	@Override
	public List<UserRole> findByUserIDs(String[] userIDs) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM USER_ROLES");
		if(userIDs != null){
			if(userIDs.length>0) 
			{
				sqlBuilder.append(" WHERE USERNAME in (?");
				for (int i=1;i<userIDs.length;i++){
					sqlBuilder.append(",?");
				}
				sqlBuilder.append(")");
			}
			
			else return new ArrayList<UserRole>(); 
		}
		List<UserRole> userRoles = jdbcTemplate.query(sqlBuilder.toString(), userIDs ,new UserRoleMapper());
		return userRoles;
	}
}
