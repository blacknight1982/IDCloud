package com.id.cloud.inspiration.dao;

import java.util.List;

import com.id.cloud.inspiration.entities.AccessLog;

public interface AccessLogDao {
	
	public int create(AccessLog accessLog);
	
	public void update(AccessLog accessLog);
	
	public AccessLog findByPrimaryKey(int id);
	
	public void deleteByPrimaryKey(int id);
	
	public void deleteAll();
	
	public List<AccessLog> findAll();
	
	public List<AccessLog> findAllDistinctIP();
	
	public List<AccessLog> findForToday();
	
	public List<AccessLog> findForTodayDistinctIP();
	
	public List<AccessLog> findForOneWeek();
	
	public List<AccessLog> findForOneWeekDistinctIP();
}
