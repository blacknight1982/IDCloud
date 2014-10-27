package com.id.cloud.inspiration.dao;

import java.util.List;

import com.id.cloud.inspiration.entities.UserRole;

public interface UserRoleDao {

	public int create(UserRole userRole);
	
	public void update(UserRole userRole);
	
	public UserRole findByPrimaryKey(int id);
	
	public void deleteByPrimaryKey(int id);
	
	public void deleteAll();
	
	public void deleteByUserIDs(String[] userIDs);
	
	public List<UserRole> findAll();
	
	public List<UserRole> findByUserIDs(String[] userIDs);
}
