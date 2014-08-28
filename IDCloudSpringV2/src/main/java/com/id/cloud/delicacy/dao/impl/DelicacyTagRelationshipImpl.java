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

import com.id.cloud.delicacy.dao.DelicacyTagRelationshipDao;
import com.id.cloud.delicacy.dao.mapping.DelicacyTagRelationshipMapper;
import com.id.cloud.delicacy.entities.DelicacyTagRelationship;

public class DelicacyTagRelationshipImpl implements DelicacyTagRelationshipDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final DelicacyTagRelationship delicacyTagRelationship) {
		if (delicacyTagRelationship == null) return 0;
		final String SQL = "insert into DELICACY_TAG_RELATION (DELICACY_ID, TAG_ID) values (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"RELATION_ID"});
	            ps.setInt(1, delicacyTagRelationship.getDelicacyID());
	            ps.setInt(2, delicacyTagRelationship.getTagID());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(DelicacyTagRelationship delicacyTagRelationship) {
		if (delicacyTagRelationship == null) return;
		String SQL = "update DELICACY_TAG_RELATION SET DELICACY_ID=?, TAG_ID=? where RELATION_ID = ? ";
		jdbcTemplate.update(SQL, delicacyTagRelationship.getDelicacyID(), delicacyTagRelationship.getTagID(), delicacyTagRelationship.getRelationID());
	}

	@Override
	public DelicacyTagRelationship getByPrimaryKey(int id) {
		String SQL = "select * from DELICACY_TAG_RELATION where RELATION_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new DelicacyTagRelationshipMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from DELICACY_TAG_RELATION where RELATION_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from DELICACY_TAG_RELATION";
		jdbcTemplate.update(SQL);
	}

}
