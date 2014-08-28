package com.id.cloud.inspiration.dao;

import java.util.List;

import com.id.cloud.inspiration.entities.Tag;

public interface TagDao {
	public int create(Tag tag);
	
	public void update(Tag tag);
	
	public Tag findByPrimaryKey(int id);
	
	public void deleteByPrimaryKey(int id);
	
	public void deleteAll();
	
	public void deleteByTagIDs(Integer[] tagIDs);
	
	public List<Tag> findAll();
	
	public List<Tag> findByTagIDs(Integer[] tagIDs);
}
