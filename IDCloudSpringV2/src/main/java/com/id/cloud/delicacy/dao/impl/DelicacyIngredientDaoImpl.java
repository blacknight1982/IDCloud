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

import com.id.cloud.delicacy.dao.DelicacyIngredientDao;
import com.id.cloud.delicacy.dao.mapping.DelicacyIngredientMapper;
import com.id.cloud.delicacy.entities.DelicacyIngredient;

public class DelicacyIngredientDaoImpl implements DelicacyIngredientDao {
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final DelicacyIngredient delicacyIngredient) {
		if (delicacyIngredient == null) return 0;
		final String SQL = "insert into DELICACY_INGREDIENT (INGREDIENT_NAME, DELICACY_ID, DELICACY_MAT_ID, INGREDIENT_UNIT, INGREDIENT_VOL) values (?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"INGREDIENT_ID"});
	            ps.setString(1, delicacyIngredient.getIngredientName());
	            ps.setInt(2, delicacyIngredient.getDelicacyID());
	            ps.setInt(3, delicacyIngredient.getDelicacyMaterialID());
	            ps.setString(4, delicacyIngredient.getIngredientUnit());
	            ps.setDouble(5, delicacyIngredient.getIngredientVolume());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(DelicacyIngredient delicacyIngredient) {
		if (delicacyIngredient == null) return;
		String SQL = "update DELICACY_INGREDIENT SET INGREDIENT_NAME=?, DELICACY_ID=?, DELICACY_MAT_ID=?, INGREDIENT_UNIT=?, INGREDIENT_VOL=? where INGREDIENT_ID = ? ";
		jdbcTemplate.update(SQL, delicacyIngredient.getIngredientName(), delicacyIngredient.getDelicacyID(), delicacyIngredient.getDelicacyMaterialID(), delicacyIngredient.getIngredientUnit(), delicacyIngredient.getIngredientVolume(), delicacyIngredient.getIngredientID());
	}

	@Override
	public DelicacyIngredient getByPrimaryKey(int id) {
		String SQL = "select * from DELICACY_INGREDIENT where INGREDIENT_ID = ? ";
		return jdbcTemplate.queryForObject(SQL,new DelicacyIngredientMapper(), id);
	}

	@Override
	public void deleteByPrimaryKey(int id) {
		String SQL = "delete from DELICACY_INGREDIENT where INGREDIENT_ID = ? ";
		jdbcTemplate.update(SQL, id);
	}

	@Override
	public void deleteAll() {
		String SQL = "delete from DELICACY_INGREDIENT";
		jdbcTemplate.update(SQL);
	}

}
