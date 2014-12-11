package com.id.cloud.inspiration.dao;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.id.cloud.inspiration.entities.Tag;

public interface TagDao {
	
	@PreAuthorize("hasRole('ROLE_USER')")
	public int create(Tag tag);
	
	@PreAuthorize("hasRole('ROLE_USER')")
	public void update(Tag tag);
	
	public Tag findByPrimaryKey(int id);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteByPrimaryKey(int id);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteAll();
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteByTagIDs(Integer[] tagIDs);
	
	public List<Tag> findAll();
	
	public List<Tag> findByTagIDs(Integer[] tagIDs);
}
