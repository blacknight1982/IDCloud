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

import com.id.cloud.delicacy.dao.DelicacyTagDao;
import com.id.cloud.delicacy.dao.mapping.DelicacyTagMapper;
import com.id.cloud.delicacy.entities.DelicacyTag;

public class DelicacyTagDaoImpl implements DelicacyTagDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final DelicacyTag delicacyTag) {
		if (delicacyTag == null) return 0;
		final String SQL = "insert into DELICACY_TAG (TAG_NAME, TAG_COLOR) values (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"TAG_ID"});
	            ps.setString(1, delicacyTag.getTagName());
	            ps.setInt(2, delicacyTag.getColor());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(DelicacyTag delicacyTag) {
		if (delicacyTag == null) return;
		String SQL = "update DELICACY_TAG SET TAG_NAME=?, TAG_COLOR=? where TAG_ID = ? ";
		jdbcTemplate.update(SQL, delicacyTag.getTagName(), delicacyTag.getColor(), delicacyTag.getTagID());
	}

	@Override
	public DelicacyTag getByPrimaryKey(int id) {
		String SQL = "select * from DELICACY_TAG where TAG_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new DelicacyTagMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from DELICACY_TAG where TAG_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from DELICACY_TAG";
		jdbcTemplate.update(SQL);
	}

}
