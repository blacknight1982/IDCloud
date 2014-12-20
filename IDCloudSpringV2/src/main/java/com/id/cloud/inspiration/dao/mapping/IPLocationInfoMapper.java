package com.id.cloud.inspiration.dao.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.id.cloud.inspiration.entities.IPLocationInfo;

public class IPLocationInfoMapper implements RowMapper<IPLocationInfo>{

	@Override
	public IPLocationInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		IPLocationInfo ipLocationInfo = new IPLocationInfo(rs.getString("IP"), rs.getString("LONGITUDE"), 
				rs.getString("LATITUDE"), rs.getString("POSTAL_CODE"), rs.getString("CITY"), rs.getString("REGION"), 
				rs.getString("REGION_CODE"), rs.getString("COUNTRY"), rs.getString("COUNTRY_CODE"), rs.getString("ISP"));
		ipLocationInfo.setIpLocationID(rs.getInt("IPLOCATION_ID"));
		return ipLocationInfo;
	}
}
