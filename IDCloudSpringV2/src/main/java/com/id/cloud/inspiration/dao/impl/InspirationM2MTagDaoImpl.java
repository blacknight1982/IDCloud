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

import com.id.cloud.inspiration.dao.InspirationM2MTagDao;
import com.id.cloud.inspiration.dao.mapping.InspirationM2MTagMapper;
import com.id.cloud.inspiration.entities.InspirationM2MTag;

@Repository
public class InspirationM2MTagDaoImpl implements InspirationM2MTagDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final InspirationM2MTag inspirationM2MTag) {
		
		if (inspirationM2MTag == null) return 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String SQL = "insert into INSPIRATION_M2M_TAG (INSPIRATION_ID, TAG_ID) values (?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"RELATION_ID"});
	            ps.setInt(1, inspirationM2MTag.getInspirationID());
	            ps.setInt(2, inspirationM2MTag.getTagID());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(InspirationM2MTag inspirationM2MTag) {
		if (inspirationM2MTag == null) return;
		String SQL = "update INSPIRATION_M2M_TAG SET INSPIRATION_ID=?, TAG_ID=? where RELATION_ID = ? ";
		jdbcTemplate.update(SQL, inspirationM2MTag.getInspirationID(), inspirationM2MTag.getTagID(), inspirationM2MTag.getRelationID());
	}

	@Override
	public InspirationM2MTag findByPrimaryKey(int id) {
		String SQL = "select * from INSPIRATION_M2M_TAG where RELATION_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new InspirationM2MTagMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from INSPIRATION_M2M_TAG where RELATION_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}
	
	@Override
	public void deleteByTagIDs(Integer[] tagIDs) {
		StringBuilder sqlBuilder = new StringBuilder("DELETE FROM INSPIRATION_M2M_TAG");
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
	public void deleteAll() {
		String SQL = "delete from INSPIRATION_M2M_TAG";
		jdbcTemplate.update(SQL);
	}
	
	@Override
	public void deleteByInspirationAndTagID(int inspirationID, int tagID) {
		String SQL = "delete from INSPIRATION_M2M_TAG where INSPIRATION_ID = ? AND TAG_ID = ?";
		jdbcTemplate.update(SQL, inspirationID, tagID);
		
	}

	@Override
	public List<InspirationM2MTag> findAll() {
		String sql = "SELECT * FROM INSPIRATION_M2M_TAG";
		List<InspirationM2MTag> inspirationM2MTags = jdbcTemplate.query(sql, new InspirationM2MTagMapper());
		return inspirationM2MTags;
	}

	@Override
	public List<InspirationM2MTag> findByInspirationID(int inspirationID) {
		String sql = "SELECT * FROM INSPIRATION_M2M_TAG WHERE INSPIRATION_ID = ?";
		List<InspirationM2MTag> inspirationM2MTags = jdbcTemplate.query(sql, new InspirationM2MTagMapper(), inspirationID);
		return inspirationM2MTags;
	}
}
