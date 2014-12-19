package com.id.cloud.inspiration.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.id.cloud.inspiration.dao.InspirationDao;
import com.id.cloud.inspiration.dao.InspirationM2MTagDao;
import com.id.cloud.inspiration.dao.TagDao;
import com.id.cloud.inspiration.dao.UserDao;
import com.id.cloud.inspiration.entities.Inspiration;
import com.id.cloud.inspiration.entities.InspirationM2MTag;
import com.id.cloud.inspiration.entities.Tag;
import com.id.cloud.inspiration.entities.User;

@Repository("idcloudInspirationAccessCacheRepository")
public class InspirationAccessCacheRepository {
	
	//private static final Logger logger = LoggerFactory.getLogger(InspirationAccessCacheRepository.class);
	
	@Autowired
	private InspirationDao inspirationDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private InspirationM2MTagDao inspirationM2MTagDao;
	
	@Autowired
	private UserDao userDao;
	
	private boolean inspirationUpdated;
	
	private boolean tagpoolUpdated;
	
	private ConcurrentSkipListMap <Integer, Tag> cachedTagPoolModel;
	
	private ConcurrentSkipListMap <Integer, Inspiration> cachedInspirationsModel;
	
	private void reloadCachedInspirationFromDB(){
		
		cachedInspirationsModel = new ConcurrentSkipListMap<Integer, Inspiration>();
		List<Inspiration> listofInspirations = inspirationDao.findAll();
		for(int i=0 ; i< listofInspirations.size();i++){
			List<InspirationM2MTag> inspirationM2MTags = inspirationM2MTagDao.findByInspirationID(listofInspirations.get(i).getInspirationID());
			Integer [] tagIDs = new Integer[inspirationM2MTags.size()];
			for (int j=0 ; j<inspirationM2MTags.size() ; j++) 
			{
				tagIDs[j] = inspirationM2MTags.get(j).getTagID();
			}
			listofInspirations.get(i).setTags(tagDao.findByTagIDs(tagIDs));
			User author = userDao.findByUsername(listofInspirations.get(i).getAuthor());
			listofInspirations.get(i).setAuthorNickname(author.getNickname());
			cachedInspirationsModel.put(listofInspirations.get(i).getInspirationID(), listofInspirations.get(i));
		}
	}
	
	private void reloadCachedTagFromDB(){
		cachedTagPoolModel = new ConcurrentSkipListMap<Integer, Tag>();
		List<Tag> listofTags = tagDao.findAll();
		for(Tag currentTag:listofTags){
			cachedTagPoolModel.put(currentTag.getTagID(), currentTag);
		}
	}
	
	@PostConstruct
	private void cacheConsistencyCheck(){
		if((null == cachedInspirationsModel)||inspirationUpdated){
			reloadCachedInspirationFromDB();
			inspirationUpdated = false;
		}
		if((null == cachedTagPoolModel)||tagpoolUpdated){
			reloadCachedTagFromDB();
			tagpoolUpdated = false;
		}
	}
	
	public void setInspirationUpdated(){
		inspirationUpdated = true;
	}
	
	public void setTagpoolUpdated(){
		tagpoolUpdated = true;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Collection <Inspiration> getFullInspirationModel(){
		this.cacheConsistencyCheck();
		return cachedInspirationsModel.values();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	public Collection <Tag> getFullTagPoolModel(){
		this.cacheConsistencyCheck();
		return cachedTagPoolModel.values();
	}
	
	public List <Inspiration> getInspirationModelByAuthorization(){
		
		this.cacheConsistencyCheck();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean hasEndUserRole = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
		boolean hasAdminRole = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		String username = auth.getName();
		List <Inspiration> inspirationModel = new ArrayList<Inspiration>();
		for(Inspiration anInspiration : cachedInspirationsModel.values()){
			if(anInspiration.getAuthLevel() == 0){
				inspirationModel.add(anInspiration);
			}
			else if((anInspiration.getAuthLevel() == 1)&&(hasEndUserRole||hasAdminRole)){
				inspirationModel.add(anInspiration);
			}
			else if((anInspiration.getAuthLevel() == 2)&&((anInspiration.getAuthor().equals(username))||hasAdminRole)){
				inspirationModel.add(anInspiration);
			}
		}	
		return inspirationModel;
	}
	
	/**
	 * If inspiration authorization level == 2 inspiration only visible to self
	 * If inspiration authorization level == 1 inspiration only visible to users with ROLE_USER authorization
	 * If inspiration authorization level == 0 inspiration visible to all the users
	 * @param id - inspiration id
	 * @return inspiration
	 */
	@PostAuthorize("((returnObject?.authLevel == 2) and ((returnObject?.author == authentication.name) or hasRole('ROLE_ADMIN'))) or"
			+ "((returnObject?.authLevel == 1) and hasRole('ROLE_USER')) or"
			+ "(returnObject?.authLevel == 0)")
	public Inspiration getInspirationByID(int inspirationID){
		
		this.cacheConsistencyCheck();
		return cachedInspirationsModel.get(inspirationID);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	public void updateInspiration(Inspiration inspiration){
		cachedInspirationsModel.put(inspiration.getInspirationID(), inspiration);
		inspirationDao.update(inspiration);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	public void createInspiration(Inspiration inspiration, List<Tag> associatedTags){
		int newInspirationID = inspirationDao.create(inspiration);
		for (Tag tag : associatedTags){
			InspirationM2MTag newInspirationM2MTag = new InspirationM2MTag(newInspirationID,tag.getTagID());
			inspirationM2MTagDao.create(newInspirationM2MTag);
		}
		inspiration.setTags(associatedTags);
		inspiration.setInspirationID(newInspirationID);
		inspiration.setAuthorNickname((userDao.findByUsername(inspiration.getAuthor()).getNickname()));
		cachedInspirationsModel.put(newInspirationID, inspiration);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteInspiration(int inspirationID){
		/**
		 *  Delete inspiration in the DB
		 */
		inspirationDao.deleteByPrimaryKey(inspirationID);
		cachedInspirationsModel.remove(inspirationID);
		
		/**
		 * Delete inspiration-tag relation
		 */
		inspirationM2MTagDao.deleteByInspirationID(inspirationID);
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteTags(Integer[] tagIDs){
		if (tagIDs == null) return;
		tagDao.deleteByTagIDs(tagIDs);
		inspirationM2MTagDao.deleteByTagIDs(tagIDs);
		for(Integer tagid:tagIDs){
			cachedTagPoolModel.remove(tagid);
		}
		inspirationUpdated = true;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void createTag(Tag newTag){
		if (newTag == null) return;
		int tagid = tagDao.create(newTag);
		newTag.setTagID(tagid);
		cachedTagPoolModel.put(tagid, newTag);
	}
}
