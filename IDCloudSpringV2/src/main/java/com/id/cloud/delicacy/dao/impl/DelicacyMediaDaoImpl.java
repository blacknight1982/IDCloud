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

import com.id.cloud.delicacy.dao.DelicacyMediaDao;
import com.id.cloud.delicacy.dao.mapping.DelicacyMediaMapper;
import com.id.cloud.delicacy.entities.DelicacyMedia;

public class DelicacyMediaDaoImpl implements DelicacyMediaDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final DelicacyMedia delicacyMedia) {
		if (delicacyMedia == null) return 0;
		final String SQL = "insert into DELICACY_MEDIA (MEDIA_NAME, MEDIA_URL, MEDIA_TYPE) values (?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"MEDIA_ID"});
	            ps.setString(1, delicacyMedia.getMediaName());
	            ps.setString(2, delicacyMedia.getMediaURL());
	            ps.setString(3, delicacyMedia.getMediaType());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(DelicacyMedia delicacyMedia) {
		if (delicacyMedia == null) return;
		String SQL = "update DELICACY_MEDIA SET MEDIA_NAME=?, MEDIA_URL=?, MEDIA_TYPE=? where MEDIA_ID = ? ";
		jdbcTemplate.update(SQL, delicacyMedia.getMediaName(), delicacyMedia.getMediaURL(), delicacyMedia.getMediaType(), delicacyMedia.getMediaID());
	}

	@Override
	public DelicacyMedia getByPrimaryKey(int id) {
		String SQL = "select * from DELICACY_MEDIA where MEDIA_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new DelicacyMediaMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from DELICACY_MEDIA where MEDIA_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from DELICACY_MEDIA";
		jdbcTemplate.update(SQL);
	}
}
