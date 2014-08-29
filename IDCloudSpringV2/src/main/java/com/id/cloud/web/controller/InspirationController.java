package com.id.cloud.web.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.id.cloud.inspiration.dao.InspirationDao;
import com.id.cloud.inspiration.dao.InspirationM2MTagDao;
import com.id.cloud.inspiration.dao.TagDao;
import com.id.cloud.inspiration.entities.Inspiration;
import com.id.cloud.inspiration.entities.InspirationM2MTag;
import com.id.cloud.inspiration.entities.Tag;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/inspiration")
public class InspirationController {
	
	private static final Logger logger = LoggerFactory.getLogger(InspirationController.class);
	
	@Autowired
	Environment environment;
		
	@Autowired
	private InspirationDao inspirationDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private InspirationM2MTagDao inspirationM2MTagDao;
	
	/**
	 * Query for the inspirations and related tags and display them on the index page
	 * Selects the index view to render by returning its name.
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String inspirationIndex(Locale locale, Model model) {
		
		List<Inspiration> inspirationList = inspirationDao.findAll();
		for(int i=0 ; i< inspirationList.size();i++){
			List<InspirationM2MTag> inspirationM2MTags = inspirationM2MTagDao.findByInspirationID(inspirationList.get(i).getInspirationID());
			Integer [] tagIDs = new Integer[inspirationM2MTags.size()];
			for (int j=0 ; j<inspirationM2MTags.size() ; j++) 
			{
				tagIDs[j] = inspirationM2MTags.get(j).getTagID();
			}
			inspirationList.get(i).setTags(tagDao.findByTagIDs(tagIDs));
		}
		model.addAttribute("inspirationList",inspirationList);
		return "index";
	}
	
	/**
	 * Publish the inspiration and associate the tags to it
	 * Selects the publish view to render by returning its name
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public String inspirationPublish(Locale locale, Model model) {
		List <Tag> tags = tagDao.findAll();
		model.addAttribute("tags", tags);
		return "publish";
	}
	
	/**
	 * Associate to post method for publishing the inspiration and associate the tags to it
	 * Redirect to the index view after executing publish
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public String inspirationPost(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		/*
		 * Create inspiration on the file system.
		 */
		
		String inspirationTitle = new String(request.getParameter("inspiration_title").getBytes("UTF-8"));
		String folderName = environment.getProperty("inspiration.folder.location")+inspirationTitle;
		String fileName = folderName+"/"+inspirationTitle+".html";
		
		(new File(folderName)).mkdirs();
		
		try{
			FileWriter fw = new FileWriter(fileName);
			fw.write(request.getParameter("inspiration_editor"));
			fw.flush();
			fw.close();
		}
		catch(IOException e){
			
		}
		
		Inspiration newInspiration = new Inspiration();
		newInspiration.setPostTime(Calendar.getInstance());
		newInspiration.setTitle(inspirationTitle);
		newInspiration.setMainPageLocation("/"+inspirationTitle+"/"+inspirationTitle+".html");
		int newInspirationID = inspirationDao.create(newInspiration);
		
		List <Tag> tags = tagDao.findAll();
		for(Tag currentTag: tags){
			if("on".equals(request.getParameter(currentTag.getName()))){
				InspirationM2MTag newInspirationM2MTag = new InspirationM2MTag(newInspirationID,currentTag.getTagID());
				inspirationM2MTagDao.create(newInspirationM2MTag);
			}	
		}
		
		return "redirect:index";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public String tagManagement(Locale locale, Model model) {
		/**
		 * Only need a list of inspiration 
		 * Tags and Inspirations will be loaded by jquery by separate view
		 */
		List<Inspiration> inspirationList = inspirationDao.findAll();
		model.addAttribute("inspirationList",inspirationList);
		return "management";
	}
	
	/**
	 * Manage the tags for each inspiration with parameter {inspiration_id}
	 * Selects the publish view to render by returning its name
	 */
	@RequestMapping(value = "/management/{inspiration_id}", method = RequestMethod.GET)
	public String inspirationTagMgt(@PathVariable String inspiration_id, Locale locale, Model model) {
		
		
		List<InspirationM2MTag> inspirationM2MTags = inspirationM2MTagDao.findByInspirationID(Integer.parseInt(inspiration_id));
		Integer [] tagIDs = new Integer[inspirationM2MTags.size()];
		for (int j=0 ; j<inspirationM2MTags.size() ; j++) 
		{
			tagIDs[j] = inspirationM2MTags.get(j).getTagID();
		}
		List <Tag> inspirationTags = tagDao.findByTagIDs(tagIDs);
		
		model.addAttribute("inspiration",inspirationDao.findByPrimaryKey(Integer.parseInt(inspiration_id)));
		model.addAttribute("inspirationTags",inspirationTags);
		
		List <Tag> tags = tagDao.findAll();
		model.addAttribute("tags", tags);
		return "management_inspirationTag";
	}
	
	/**
	 * Manage the tags for creating, deleting in a pool
	 * Selects the publish view to render by returning its name
	 */
	@RequestMapping(value = "/management/create_delete", method = RequestMethod.POST)
	public String tagCreateDeletePool(@RequestParam(value = "tagNameArray[]", required = false) String[] tagNameArray, 
			@RequestParam(value = "tagColorArray[]", required = false) String[] tagColorArray,
			@RequestParam(value = "removeTagIDs", required = false) String removeTagIDs,
			ModelAndView model) {
		if ((removeTagIDs != null)&&(removeTagIDs.length()>0)){
			String tagIDs[] = removeTagIDs.split(",");
			Integer[] intTagIDs = new Integer[tagIDs.length];
			System.out.println("removeTagIDs: "+removeTagIDs);
			for (int i=0;i<tagIDs.length;i++){
				
				intTagIDs[i] = Integer.valueOf(tagIDs[i]);
			}
			tagDao.deleteByTagIDs(intTagIDs);
			inspirationM2MTagDao.deleteByTagIDs(intTagIDs);
		}
		
		if ((tagNameArray!=null)&&
				(tagColorArray!=null)&&
				(tagNameArray.length>0)&&
				(tagColorArray.length>0)&&
				tagNameArray.length == tagColorArray.length){
			for (int i=0;i<tagNameArray.length;i++){
				Tag newTag = new Tag(tagNameArray[i],tagColorArray[i]);
				tagDao.create(newTag);
			}
		}
		
		return "tagpool";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@RequestMapping(value = "/management/tagpool", method = RequestMethod.GET)
	public String tagPool(Model model) {
		List <Tag> tags = tagDao.findAll();
		model.addAttribute("tags", tags);
		return "tagpool";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@RequestMapping(value = "/management/inspirationpool", method = RequestMethod.GET)
	public String inspirationPool(Model model) {
		List<Inspiration> inspirationList = inspirationDao.findAll();
		model.addAttribute("inspirationList",inspirationList);
		return "inspirationpool";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@RequestMapping(value = "/management/inspirationremove/{inspiration_id}", method = RequestMethod.GET)
	public String inspirationRemove(@PathVariable String inspiration_id, Model model) {
		int inspirationID = Integer.parseInt(inspiration_id);
		
		/**
		 * Delete inspiration on the file system
		 */
		
		Inspiration inspiration = inspirationDao.findByPrimaryKey(inspirationID);
		try {
		String folderName = environment.getProperty("inspiration.folder.location") + inspiration.getTitle();
		
			FileUtils.deleteDirectory(new File(folderName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Delete Inspiration:" + inspiration.getTitle() + "on the file system failed");
			e.printStackTrace(System.err);
		}
		
		
		/**
		 *  Delete inspiration in the DB
		 */
		
		inspirationDao.deleteByPrimaryKey(inspirationID);
		
		
		
		List<Inspiration> inspirationList = inspirationDao.findAll();
		model.addAttribute("inspirationList",inspirationList);
		return "inspirationpool";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@RequestMapping(value = "/management/inspirationtags/{inspiration_id}", method = RequestMethod.POST)
	public String inspirationTagOper(@PathVariable String inspiration_id, 
			@RequestParam(value = "inspirationTagOperation", required = false) String inspirationTagOperation,
			ModelAndView model) {
		int inspirationID = Integer.parseInt(inspiration_id);
		if ((inspirationTagOperation != null)&&(inspirationTagOperation.length()>0)){
			String inspirationTagOperations[] = inspirationTagOperation.split(",");
			int intTagOperations[] = new int[inspirationTagOperations.length];
			for(int i=0;i<inspirationTagOperations.length;i++){
				intTagOperations[i] = Integer.parseInt(inspirationTagOperations[i]);
			}
			
			//Check duplicating add and delete			
			for(int i=0;i<intTagOperations.length;i++){
				if(intTagOperations[i]!=0){
					for(int j=i+1;j<inspirationTagOperations.length;j++){
						if((intTagOperations[i]+intTagOperations[j])==0){
							intTagOperations[i]=0;
							intTagOperations[j]=0;
							break;
						}
					}
				}
			}
			
			//doing inspiration tag adding deleting operation
			for(int i=0;i<intTagOperations.length;i++){
				if(intTagOperations[i]>0){
					InspirationM2MTag inspirationM2MTag = new InspirationM2MTag(inspirationID,intTagOperations[i]);
					inspirationM2MTagDao.create(inspirationM2MTag);
				}
				else if(intTagOperations[i]<0){
					inspirationM2MTagDao.deleteByInspirationAndTagID(inspirationID, 0-intTagOperations[i]);
				}
			}
		}
		
		return "management";
	}
	
	/**
	 * Test Servlet
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
		String formattedDate = dateFormat.format(date);		
		model.addAttribute("serverTime", formattedDate );
		return "dataAccessFailure";
	}
	
}
