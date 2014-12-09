package com.id.cloud.inspiration.dao;

import java.util.List;

import com.id.cloud.inspiration.entities.User;

public interface UserDao {

	public int create(User user);
	
	public void update(User user);
	
	public User findByPrimaryKey(String username);
	
	public void deleteByPrimaryKey(String username);
	
	public void deleteAll();
	
	public void deleteByUserIDs(String[] userIDs);
	
	public List<User> findAll();
	
	public List<User> findByUserIDs(String[] userIDs);
}
