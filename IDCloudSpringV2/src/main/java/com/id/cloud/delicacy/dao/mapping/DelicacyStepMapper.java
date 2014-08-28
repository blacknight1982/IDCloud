package com.id.cloud.delicacy.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.delicacy.entities.DelicacyStep;

public class DelicacyStepMapper implements RowMapper<DelicacyStep>{

	@Override
	public DelicacyStep mapRow(ResultSet rs, int rowNum) throws SQLException {
		DelicacyStep delicacyStep = new DelicacyStep();
		delicacyStep.setDelicacyID(rs.getInt("DELICACY_ID"));
		delicacyStep.setStepDescription(rs.getString("STEP_DESCRIPTION"));
		delicacyStep.setStepID(rs.getInt("STEP_ID"));
		delicacyStep.setStepName(rs.getString("STEP_NAME"));
		delicacyStep.setStepNum(rs.getInt("STEP_NUM"));
		delicacyStep.setStepTime(rs.getInt("STEP_TIME"));
		return delicacyStep;
	}
}
