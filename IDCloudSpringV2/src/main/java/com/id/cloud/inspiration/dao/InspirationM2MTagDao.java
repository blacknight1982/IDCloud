package com.id.cloud.inspiration.dao;

import java.util.List;

import com.id.cloud.inspiration.entities.InspirationM2MTag;

public interface InspirationM2MTagDao {
	
	public int create(InspirationM2MTag inspirationM2MTag);
	
	public void update(InspirationM2MTag inspirationM2MTag);
	
	public InspirationM2MTag findByPrimaryKey(int id);
	
	public void deleteByPrimaryKey(int id);
	
	public void deleteAll();
	
	public void deleteByTagIDs(Integer[] tagIDs);
	
	public void deleteByInspirationAndTagID(int inspirationID, int tagID);
	
	public void deleteByInspirationID(int inspirationID) ;
	
	public List<InspirationM2MTag> findAll();
	
	public List<InspirationM2MTag> findByInspirationID(int inspirationID);
}
