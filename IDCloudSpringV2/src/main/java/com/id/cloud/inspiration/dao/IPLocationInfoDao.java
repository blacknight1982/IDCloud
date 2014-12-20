package com.id.cloud.inspiration.dao;

import java.util.List;

import com.id.cloud.inspiration.entities.IPLocationInfo;

public interface IPLocationInfoDao {
	
	public int create(IPLocationInfo ipLocationInfo);
	
	public void update(IPLocationInfo ipLocationInfo);
	
	public List<IPLocationInfo> findAll();
	
	public List<IPLocationInfo> findByIPs(String[] ips);
}
