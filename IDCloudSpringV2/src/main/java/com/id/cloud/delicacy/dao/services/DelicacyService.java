package com.id.cloud.delicacy.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.id.cloud.delicacy.dao.DelicacyDao;
import com.id.cloud.delicacy.dao.DelicacyIngredientDao;
import com.id.cloud.delicacy.entities.Delicacy;
import com.id.cloud.delicacy.entities.DelicacyIngredient;

public class DelicacyService {
	
	@Autowired
    private TransactionTemplate transactionTemplate;
	
	@Autowired
	private DelicacyDao delicacyDao;
	
	@Autowired
	private DelicacyIngredientDao delicacyIngredientDao;
	
    public void createDelicacyWithIngredients(final Delicacy delicacy,
            final List<DelicacyIngredient> ingredients) {
    	transactionTemplate.execute(new TransactionCallbackWithoutResult() {
    		
            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	int delicacyID = delicacyDao.create(delicacy);
                for (DelicacyIngredient ingredient:ingredients){
                	ingredient.setDelicacyID(delicacyID);
                	delicacyIngredientDao.create(ingredient);
                }
            }
        });
    }
}
