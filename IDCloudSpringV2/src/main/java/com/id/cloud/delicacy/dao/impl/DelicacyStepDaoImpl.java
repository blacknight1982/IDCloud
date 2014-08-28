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

import com.id.cloud.delicacy.dao.DelicacyStepDao;
import com.id.cloud.delicacy.dao.mapping.DelicacyStepMapper;
import com.id.cloud.delicacy.entities.DelicacyStep;

@Repository
public class DelicacyStepDaoImpl implements DelicacyStepDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int create(final DelicacyStep delicacyStep) {
		if(delicacyStep == null) return 0;
		final String SQL = "insert into DELICACY_STEP (STEP_NUM, STEP_NAME, STEP_DESCRIPTION, STEP_TIME, DELICACY_ID) values (?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"STEP_ID"});
	            ps.setInt(1, delicacyStep.getStepNum());
	            ps.setString(2, delicacyStep.getStepName());
	            ps.setString(3, delicacyStep.getStepDescription());
	            ps.setInt(4, delicacyStep.getStepTime());
	            ps.setInt(5, delicacyStep.getDelicacyID());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(DelicacyStep delicacyStep) {
		if (delicacyStep == null) return;
		String SQL = "update DELICACY_STEP SET STEP_NUM=?, STEP_NAME=?, STEP_DESCRIPTION=?, STEP_TIME=?, DELICACY_ID=? where STEP_ID = ? ";
		jdbcTemplate.update(SQL, delicacyStep.getStepNum(), delicacyStep.getStepName(), delicacyStep.getStepDescription(), delicacyStep.getStepTime(), delicacyStep.getDelicacyID(), delicacyStep.getStepID());
	}

	@Override
	public DelicacyStep getByPrimaryKey(int id) {
		String SQL = "select * from DELICACY_STEP where STEP_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new DelicacyStepMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from DELICACY_STEP where STEP_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from DELICACY";
		jdbcTemplate.update(SQL);
	}
}
