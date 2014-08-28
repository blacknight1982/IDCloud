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

import com.id.cloud.delicacy.dao.DelicacyObjMediaRelationDao;
import com.id.cloud.delicacy.dao.mapping.DelicacyObjMediaRelationMapper;
import com.id.cloud.delicacy.entities.DelicacyObjMediaRelation;

public class DelicacyObjMediaRelationDaoImpl implements
		DelicacyObjMediaRelationDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final DelicacyObjMediaRelation delicacyObjMediaRelation) {
		if (delicacyObjMediaRelation == null) return 0;
		final String SQL = "insert into DELICACY_OBJ_MED_REL (OBJ_ID, MEDIA_ID, DELICACY_OBJ_TYPE) values (?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"RELATION_ID"});
	            ps.setInt(1, delicacyObjMediaRelation.getObjectID());
	            ps.setInt(2, delicacyObjMediaRelation.getMediaID());
	            ps.setInt(3, delicacyObjMediaRelation.getDelicacyObjType().getValue());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(DelicacyObjMediaRelation delicacyObjMediaRelation) {
		if (delicacyObjMediaRelation == null) return;
		String SQL = "update DELICACY_OBJ_MED_REL SET OBJ_ID=?, MEDIA_ID=?, DELICACY_OBJ_TYPE=? where RELATION_ID = ? ";
		jdbcTemplate.update(SQL, delicacyObjMediaRelation.getObjectID(), delicacyObjMediaRelation.getMediaID(), delicacyObjMediaRelation.getDelicacyObjType().getValue(), delicacyObjMediaRelation.getRelationID());
	}

	@Override
	public DelicacyObjMediaRelation getByPrimaryKey(int id) {
		String SQL = "select * from DELICACY_OBJ_MED_REL where RELATION_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new DelicacyObjMediaRelationMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from DELICACY_OBJ_MED_REL where RELATION_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from DELICACY_OBJ_MED_REL";
		jdbcTemplate.update(SQL);
	}
}
