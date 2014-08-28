package com.id.cloud.delicacy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.id.cloud.delicacy.dao.DelicacyDao;
import com.id.cloud.delicacy.dao.mapping.DelicacyMapper;
import com.id.cloud.delicacy.entities.Delicacy;

@Repository
public class DelicacyDaoImpl implements DelicacyDao {

	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final Delicacy delicacy) {
		if (delicacy == null) return 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String SQL = "insert into DELICACY (DELICACY_NAME, DELICACY_STORY, DELICACY_HIGHLIGHT) values (?, ?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"DELICACY_ID"});
	            ps.setString(1, delicacy.getDelicacyName());
	            ps.setString(2, delicacy.getDelicacyHighlight());
	            ps.setString(3, delicacy.getDelicacyStory());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public Delicacy getByPrimaryKey(int id) {
		String SQL = "select * from DELICACY where DELICACY_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new DelicacyMapper(), id);
	}

	@Override
	public void update(Delicacy delicacy) {
		if (delicacy == null) return;
		String SQL = "update DELICACY SET DELICACY_NAME=?, DELICACY_STORY=?, DELICACY_HIGHLIGHT=? where DELICACY_ID = ? ";
		jdbcTemplate.update(SQL, delicacy.getDelicacyName(), delicacy.getDelicacyStory(), delicacy.getDelicacyHighlight(), delicacy.getDelicacyID());
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from DELICACY where DELICACY_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from DELICACY";
		jdbcTemplate.update(SQL);
	}
}
