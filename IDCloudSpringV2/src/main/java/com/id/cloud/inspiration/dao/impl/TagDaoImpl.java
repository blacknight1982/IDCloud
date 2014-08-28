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

import com.id.cloud.inspiration.dao.TagDao;
import com.id.cloud.inspiration.dao.mapping.TagMapper;
import com.id.cloud.inspiration.entities.Tag;

@Repository
public class TagDaoImpl implements TagDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final Tag tag) {
		if(tag == null) return 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String SQL = "insert into TAG (TAG_NAME, TAG_COLOR) values (?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"TAG_ID"});
	            ps.setString(1, tag.getName());
	            ps.setString(2, tag.getColor());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(Tag tag) {
		if (tag == null) return;
		String SQL = "update TAG SET TAG_NAME=?, TAG_COLOR=? where TAG_ID = ? ";
		jdbcTemplate.update(SQL, tag.getName(), tag.getColor(), tag.getTagID());

	}

	@Override
	public Tag findByPrimaryKey(int id) {
		String SQL = "select * from TAG where TAG_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new TagMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from TAG where TAG_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from TAG";
		jdbcTemplate.update(SQL);
	}
	
	@Override
	public void deleteByTagIDs(Integer[] tagIDs) {
		StringBuilder sqlBuilder = new StringBuilder("DELETE FROM TAG");
		if(tagIDs != null){
			if(tagIDs.length>0){
				sqlBuilder.append(" WHERE TAG_ID in (?");
				for (int i=1;i<tagIDs.length;i++){
					sqlBuilder.append(",?");
				}
				sqlBuilder.append(")");
			}
			else return;
		}
		jdbcTemplate.update(sqlBuilder.toString(), (Object[])tagIDs);
	}

	@Override
	public List<Tag> findAll() {
		String sql = "SELECT * FROM TAG";
		List<Tag> tags = jdbcTemplate.query(sql, new TagMapper());
		return tags;
	}

	@Override
	public List<Tag> findByTagIDs(Integer[] tagIDs) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM TAG");
		if(tagIDs != null){
			if(tagIDs.length>0) 
			{
				sqlBuilder.append(" WHERE TAG_ID in (?");
				for (int i=1;i<tagIDs.length;i++){
					sqlBuilder.append(",?");
				}
				sqlBuilder.append(")");
			}
			
			else return new ArrayList<Tag>(); 
		}
		List<Tag> tags = jdbcTemplate.query(sqlBuilder.toString(), tagIDs ,new TagMapper());
		return tags;
	}
}
