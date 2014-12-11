package com.id.cloud.inspiration.dao;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.id.cloud.inspiration.entities.User;

public interface UserDao {

	public int create(User user);
	
	@PreAuthorize("#user.getUsername().equals(authentication.name) or hasRole('ROLE_ADMIN')")
	public void update(User user);
	
	public User findByPrimaryKey(int id);
	
	public User findByUsername(String username);
	
	public void deleteByPrimaryKey(String username);
	
	public void deleteAll();
	
	public void deleteByUserIDs(String[] userIDs);
	
	public List<User> findAll();
	
	public List<User> findByUserIDs(String[] userIDs);
}
