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

import com.id.cloud.delicacy.dao.DelicacyMaterialDao;
import com.id.cloud.delicacy.dao.mapping.DelicacyMaterialMapper;
import com.id.cloud.delicacy.entities.DelicacyMaterial;

public class DelicacyMaterialDaoImpl implements DelicacyMaterialDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final DelicacyMaterial delicacyMaterial) {
		if (delicacyMaterial == null) return 0;
		final String SQL = "insert into DELICACY_MATERIAL (MATERIAL_NAME, MATERIAL_DES, MATERIAL_TYPE) values (?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"MATERIAL_ID"});
	            ps.setString(1, delicacyMaterial.getMaterialName());
	            ps.setString(2, delicacyMaterial.getMaterialDescription());
	            ps.setString(3, delicacyMaterial.getMaterialType());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(DelicacyMaterial delicacyMaterial) {
		if (delicacyMaterial == null) return;
		String SQL = "update DELICACY_MATERIAL SET MATERIAL_NAME=?, MATERIAL_DES=?, MATERIAL_TYPE=? where MATERIAL_ID = ? ";
		jdbcTemplate.update(SQL, delicacyMaterial.getMaterialName(), delicacyMaterial.getMaterialDescription(), delicacyMaterial.getMaterialType(), delicacyMaterial.getMaterialID());
	}

	@Override
	public DelicacyMaterial getByPrimaryKey(int id) {
		String SQL = "select * from DELICACY_MATERIAL where MATERIAL_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new DelicacyMaterialMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from DELICACY_MATERIAL where MATERIAL_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from DELICACY_MATERIAL";
		jdbcTemplate.update(SQL);
	}
}
