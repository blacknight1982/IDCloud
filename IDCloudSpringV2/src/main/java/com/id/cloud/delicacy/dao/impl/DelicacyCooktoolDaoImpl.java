package com.id.cloud.delicacy.dao.impl;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.id.cloud.delicacy.dao.DelicacyCooktoolDao;
import com.id.cloud.delicacy.entities.DelicacyCooktool;

@Repository
public class DelicacyCooktoolDaoImpl implements DelicacyCooktoolDao {
	
	//@PersistenceContext
	private EntityManager em;
	
	//@Override
	public void saveOrUpdate(DelicacyCooktool cookTool) {
		em.persist(cookTool);
	}
}
