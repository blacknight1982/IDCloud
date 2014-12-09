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

import com.id.cloud.inspiration.dao.InspirationDao;
import com.id.cloud.inspiration.dao.mapping.InspirationMapper;
import com.id.cloud.inspiration.entities.Inspiration;

@Repository
public class InspirationDaoImpl implements InspirationDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final Inspiration inspiration) {
		if (inspiration == null) return 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String SQL = "insert into INSPIRATION (INSPIRATION_UUID, INSPIRATION_TITLE, INSPIRATION_LOCATION, INSPIRATION_POSTTIME, INSPIRATION_AUTHOR,INSPIRATION_BRIEFING, AUTH_LEVEL) values (?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"INSPIRATION_ID"});
	            ps.setString(1, inspiration.getUuid());
	            ps.setString(2, inspiration.getTitle());
	            ps.setString(3, inspiration.getMainPageLocation());
	            ps.setTimestamp(4, new java.sql.Timestamp(inspiration.getPostTime().getTimeInMillis()), inspiration.getPostTime());
	            ps.setString(5, inspiration.getAuthor());
	            ps.setString(6, inspiration.getBriefing());
	            ps.setInt(7, inspiration.getAuthLevel());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(Inspiration inspiration) {
		if (inspiration == null) return;
		String SQL = "update INSPIRATION SET INSPIRATION_TITLE=?, INSPIRATION_LOCATION=?, INSPIRATION_POSTTIME=?, INSPIRATION_AUTHOR=?, INSPIRATION_BRIEFING=?, AUTH_LEVEL=? where INSPIRATION_ID = ? ";
		jdbcTemplate.update(SQL, inspiration.getTitle(), inspiration.getMainPageLocation(), new java.sql.Timestamp(inspiration.getPostTime().getTimeInMillis()), inspiration.getAuthor(), inspiration.getBriefing(),inspiration.getAuthLevel(), inspiration.getInspirationID());
	}

	@Override
	public Inspiration findByPrimaryKey(int id) {
		String SQL = "select * from INSPIRATION where INSPIRATION_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new InspirationMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from INSPIRATION where INSPIRATION_ID = ? ";
		jdbcTemplate.update(SQL, id);

	}

	@Override
	public void deleteAll() {
		String SQL = "delete from INSPIRATION";
		jdbcTemplate.update(SQL);
	}
	
	@Override
	public List<Inspiration> findAll(){
		String sql = "SELECT * FROM INSPIRATION";
		List<Inspiration> inspirations = jdbcTemplate.query(sql, new InspirationMapper());
		return inspirations;
	}

}
