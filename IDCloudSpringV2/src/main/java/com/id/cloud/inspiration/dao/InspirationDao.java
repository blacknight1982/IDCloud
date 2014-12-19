package com.id.cloud.inspiration.dao;

import java.util.List;

import com.id.cloud.inspiration.entities.Inspiration;

public interface InspirationDao {
	
	public int create(Inspiration inspiration);
	
	public void update(Inspiration inspiration);
	
	public Inspiration findByPrimaryKey(int id);
	
	public void deleteByPrimaryKey(int id);
	
	public void deleteAll();
	
	public List<Inspiration> findAll();
	
	public List<Inspiration> findAllOrderByTime();
}
