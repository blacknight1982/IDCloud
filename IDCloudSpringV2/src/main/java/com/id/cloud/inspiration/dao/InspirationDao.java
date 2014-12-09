package com.id.cloud.inspiration.dao;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;

import com.id.cloud.inspiration.entities.Inspiration;

public interface InspirationDao {
	
	public int create(Inspiration inspiration);
	
	public void update(Inspiration inspiration);
	
	/**
	 * If inspiration authorization level == 2 inspiration only visible to self
	 * @param id - inspiration id
	 * @return inspiration
	 */
	@PostAuthorize("returnObject?.authLevel < 2 or returnObject?.author == authentication.name or hasRole('ROLE_ADMIN')")
	public Inspiration findByPrimaryKey(int id);
	
	public void deleteByPrimaryKey(int id);
	
	public void deleteAll();
	
	public List<Inspiration> findAll();
}
