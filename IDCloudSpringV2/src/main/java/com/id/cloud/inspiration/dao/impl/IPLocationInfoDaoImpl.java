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

import com.id.cloud.inspiration.dao.IPLocationInfoDao;
import com.id.cloud.inspiration.dao.mapping.IPLocationInfoMapper;
import com.id.cloud.inspiration.entities.IPLocationInfo;

@Repository
public class IPLocationInfoDaoImpl implements IPLocationInfoDao {

	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(final IPLocationInfo ipLocationInfo) {
		if (ipLocationInfo == null) return 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String SQL = "insert into IPLOCATIONINFO (IP, LONGITUDE, LATITUDE, POSTAL_CODE, CITY, REGION, REGION_CODE, COUNTRY, COUNTRY_CODE, ISP) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	            PreparedStatement ps =
	                connection.prepareStatement(SQL, new String[] {"IPLOCATION_ID"});
	            ps.setString(1, ipLocationInfo.getIp());
	            ps.setString(2, ipLocationInfo.getLongitude());
	            ps.setString(3, ipLocationInfo.getLatitude());
	            ps.setString(4, ipLocationInfo.getPostal_code());
	            ps.setString(5, ipLocationInfo.getCity());
	            ps.setString(6, ipLocationInfo.getRegion());
	            ps.setString(7, ipLocationInfo.getRegion_code());
	            ps.setString(8, ipLocationInfo.getCountry());
	            ps.setString(9, ipLocationInfo.getCountry_code());
	            ps.setString(10, ipLocationInfo.getIsp());
	            return ps;
	        }
	    },
	    keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void update(final IPLocationInfo ipLocationInfo) {
		if (ipLocationInfo == null) return;
		String SQL = "update IPLOCATIONINFO SET IP=?, LONGITUDE=?, LATITUDE=?, POSTAL_CODE=?, CITY=?, REGION=?, REGION_CODE=?, COUNTRY=?, COUNTRY_CODE=?, ISP=? where IPLOCATION_ID = ? ";
		jdbcTemplate.update(SQL, ipLocationInfo.getIp(), ipLocationInfo.getLongitude(), ipLocationInfo.getLatitude(), ipLocationInfo.getPostal_code(), ipLocationInfo.getCity(), ipLocationInfo.getRegion(), ipLocationInfo.getRegion_code(), ipLocationInfo.getCountry(), ipLocationInfo.getCountry_code(), ipLocationInfo.getIsp());
	}
	
	@Override
	public List<IPLocationInfo> findAll() {
		String sql = "SELECT * FROM IPLOCATIONINFO";
		List<IPLocationInfo> ipLocations = jdbcTemplate.query(sql, new IPLocationInfoMapper());
		return ipLocations;
	}

	@Override
	public List<IPLocationInfo> findByIPs(String[] ips) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM IPLOCATIONINFO");
		if(ips != null){
			if(ips.length>0) 
			{
				sqlBuilder.append(" WHERE IP in (?");
				for (int i=1;i<ips.length;i++){
					sqlBuilder.append(",?");
				}
				sqlBuilder.append(")");
			}
			
			else return new ArrayList<IPLocationInfo>(); 
		}
		List<IPLocationInfo> ipLocations = jdbcTemplate.query(sqlBuilder.toString(), ips ,new IPLocationInfoMapper());
		return ipLocations;
	}

}
