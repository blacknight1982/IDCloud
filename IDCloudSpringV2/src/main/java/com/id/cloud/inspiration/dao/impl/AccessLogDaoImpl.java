package com.id.cloud.inspiration.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.id.cloud.inspiration.dao.AccessLogDao;
import com.id.cloud.inspiration.dao.mapping.AccessLogMapper;
import com.id.cloud.inspiration.entities.AccessLog;

@Repository
public class AccessLogDaoImpl implements AccessLogDao {

	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final AccessLog accessLog) {
		if (accessLog == null) return 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String SQL = "insert into ACCESSLOG (IP, ACCESS_TIME, USER_AGENT, ACCESS_MODULE, USER) values (?, ?, ?, ?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"ACCESS_ID"});
	            ps.setString(1, accessLog.getIp());
	            ps.setTimestamp(2, new java.sql.Timestamp(accessLog.getTime().getTimeInMillis()), accessLog.getTime());
	            ps.setString(3, accessLog.getUserAgent());
	            ps.setString(4, accessLog.getAccessModule());
	            ps.setString(5, accessLog.getUser());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(AccessLog accessLog) {
		if (accessLog == null) return;
		String SQL = "update ACCESSLOG SET IP=?, ACCESS_TIME=?, INSPIRATION_POSTTIME=?, USER_AGENT=?, ACCESS_MODULE=?, USER=? where ACCESS_ID = ? ";
		jdbcTemplate.update(SQL, accessLog.getIp(), new java.sql.Timestamp(accessLog.getTime().getTimeInMillis()), accessLog.getUserAgent(), accessLog.getAccessModule(), accessLog.getUser(), accessLog.getAccessid());
	}

	@Override
	public AccessLog findByPrimaryKey(int id) {
		String SQL = "select * from ACCESSLOG where ACCESS_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new AccessLogMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from ACCESSLOG where ACCESS_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from ACCESSLOG";
		jdbcTemplate.update(SQL);
	}

	@Override
	public List<AccessLog> findAll() {
		String sql = "SELECT * FROM ACCESSLOG";
		List<AccessLog> accessLogs = jdbcTemplate.query(sql, new AccessLogMapper());
		return accessLogs;
	}

}
