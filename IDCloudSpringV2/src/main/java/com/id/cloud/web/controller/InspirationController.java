package com.id.cloud.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.id.cloud.inspiration.dao.AccessLogDao;
import com.id.cloud.inspiration.dao.InspirationM2MTagDao;
import com.id.cloud.inspiration.dao.UserDao;
import com.id.cloud.inspiration.entities.AccessLog;
import com.id.cloud.inspiration.entities.Inspiration;
import com.id.cloud.inspiration.entities.InspirationM2MTag;
import com.id.cloud.inspiration.entities.Tag;
import com.id.cloud.inspiration.repository.InspirationAccessCacheRepository;
import com.id.cloud.inspiration.repository.InspirationAccessLocationRepository;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/inspiration")
public class InspirationController {
	
	private static final Logger logger = LoggerFactory.getLogger(InspirationController.class);
	
	@Autowired
	private AbstractMessageSource messagesource;
	
	@Autowired
	private InspirationM2MTagDao inspirationM2MTagDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccessLogDao accessLogDao;
	
	@Autowired
	private InspirationAccessCacheRepository inspirationAccessCacheRepository;
	
	@Autowired
	private InspirationAccessLocationRepository inspirationAccessLocationRepository;
	
	/**
	 * Query for the inspirations and related tags and display them on the index page
	 * Selects the index view to render by returning its name.
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String inspirationIndex(Locale locale, Model model, HttpServletRequest request) {
		final String userIpAddress = request.getRemoteAddr();
        final String userAgent = request.getHeader("user-agent");
        final String user = request.getRemoteUser();
        AccessLog accessLog = new AccessLog(userIpAddress, userAgent, "Inspiration Index",user);
        accessLogDao.create(accessLog);
        inspirationAccessLocationRepository.recordAccessLocation(accessLog);
        model.addAttribute("inspirationList",inspirationAccessCacheRepository.getInspirationModelByAuthorization());
		return "index";
	}
	
	/**
	 * Publish the inspiration and associate the tags to it
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public String inspirationPublish(Locale locale, Model model) {
		model.addAttribute("tags", inspirationAccessCacheRepository.getFullTagPoolModel());
		return "publish";
	}
	
	/**
	 * Associate to post method for publishing the inspiration and associate the tags to it
	 * Redirect to the index view after executing publish
	 * @throws UnsupportedEncodingException 
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public String inspirationPost(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		
		/*
		 * Create inspiration on the file system.
		 * Two copies, one for locale en and one for locale zh
		 */
		String uuid = UUID.randomUUID().toString();
		String inspirationTitle = request.getParameter("inspiration_title");
		
		String folderName_en = messagesource.getMessage("inspiration.webcontents.folder.location", null,Locale.US)+"/"+uuid;
		String fileName_en = folderName_en+"/"+uuid+".html";
		
		String folderName_zh = messagesource.getMessage("inspiration.webcontents.folder.location", null,Locale.CHINA)+"/"+uuid;
		String fileName_zh = folderName_zh+"/"+uuid+".html";
		
		(new File(folderName_en)).mkdirs();
		
		(new File(folderName_zh)).mkdirs();
		
		try{
			FileOutputStream fos_en = new FileOutputStream(fileName_en);
			OutputStreamWriter osw_en = new OutputStreamWriter(fos_en,"UTF-8");
			osw_en.write(request.getParameter("inspiration_editor"));
			osw_en.flush();
			osw_en.close();
			
			FileOutputStream fos_zh = new FileOutputStream(fileName_zh);
			OutputStreamWriter osw_zh = new OutputStreamWriter(fos_zh,"UTF-8");
			osw_zh.write(request.getParameter("inspiration_editor"));
			osw_zh.flush();
			osw_zh.close();
		}
		catch(IOException e){
			logger.error("Inspiration publish with error: "+e.getMessage());
		}
		//get inspiration authentication level
		String auth_level = request.getParameter("inspiration-auth-level");
	
		
		Inspiration newInspiration = new Inspiration();
		newInspiration.setUuid(uuid);
		newInspiration.setPostTime(Calendar.getInstance());
		newInspiration.setTitle(inspirationTitle);
		newInspiration.setMainPageLocation("/"+uuid+"/"+uuid+".html");
		newInspiration.setAuthLevel(Integer.parseInt(auth_level));
		newInspiration.setBriefing(request.getParameter("inspiration_briefing"));
		
		//set inspiration author to the current user
		newInspiration.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
		
		List <Tag> associatedTags = new ArrayList<Tag>();
		
		
		for(Tag currentTag: inspirationAccessCacheRepository.getFullTagPoolModel()){
			if("on".equals(request.getParameter(currentTag.getName()))){
				associatedTags.add(currentTag);
			}	
		}
		inspirationAccessCacheRepository.createInspiration(newInspiration,associatedTags);
		
		return "redirect:index";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public String tagManagement(Locale locale, Model model) {
		/**
		 * Only need a list of inspiration 
		 * Tags and Inspirations will be loaded by jquery by separate view
		 */
		model.addAttribute("inspirationList",inspirationAccessCacheRepository.getFullInspirationModel());
		return "management";
	}
	
	/**
	 * Manage the tags for each inspiration with parameter {inspiration_id}
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/management/{inspiration_id}", method = RequestMethod.GET)
	public String inspirationTagMgt(@PathVariable int inspiration_id, Locale locale, Model model) {
		
		Inspiration inspiration = inspirationAccessCacheRepository.getInspirationByID(inspiration_id);
		List <Tag> inspirationTags = inspiration.getTags();
		
		model.addAttribute("inspiration",inspiration);
		model.addAttribute("inspirationTags",inspirationTags);
		
		model.addAttribute("tags", inspirationAccessCacheRepository.getFullTagPoolModel());
		return "inspirationtag";
	}
	
	/**
	 * Direct link for getting the content of each inspiration with parameter {inspiration_id}
	 */
	@RequestMapping(value = "/{inspiration_id}", method = RequestMethod.GET)
	public String directLink(@PathVariable int inspiration_id, Locale locale, Model model) {
		
		model.addAttribute("inspiration",inspirationAccessCacheRepository.getInspirationByID(inspiration_id));
		return "directlink";
	}
	
	/**
	 * Manage the content for each inspiration with parameter {inspiration_id}
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{inspiration_id}", method = RequestMethod.POST)
	public String inspirationEditPost(@PathVariable int inspiration_id, Locale locale, Model model, HttpServletRequest request) {
		
		
		Inspiration inspiration = inspirationAccessCacheRepository.getInspirationByID(inspiration_id);
	
		
		String article_update = request.getParameter("inspiration_article_update"); 
		String inspirationTitle = request.getParameter("inspiration_title-edit");
		String updatelocale = request.getParameter("inspiration-language");
		
		String uuid = inspiration.getUuid();
		
		if("update".equals(article_update)){
			
			/**
			 * Write edited inspiration on the file system.
			 */
			String folderName = null;
			String fileName = null;
			if("en".equals(updatelocale))
			{
				folderName = messagesource.getMessage("inspiration.webcontents.folder.location", null,Locale.US)+"/"+uuid;
				
			}
			else if("zh".equals(updatelocale)){
				folderName = messagesource.getMessage("inspiration.webcontents.folder.location", null,Locale.CHINA)+"/"+uuid;
			}
			else{
				folderName = messagesource.getMessage("inspiration.webcontents.folder.location", null,locale)+"/"+uuid;
			}
			
			fileName = folderName+"/"+uuid+".html";
			
			try{
				FileOutputStream fos = new FileOutputStream(fileName);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
				osw.write(request.getParameter("inspiration_editor-edit"));
				osw.flush();
				osw.close();
			}
			catch(IOException e){
				logger.error("Inspiration update with error: "+e.getMessage());
			}
		}
		
		
		//get inspiration authentication level
		String auth_level = request.getParameter("inspiration-auth-level");
		
		inspiration.setPostTime(Calendar.getInstance());
		inspiration.setTitle(inspirationTitle);
		inspiration.setMainPageLocation("/"+uuid+"/"+uuid+".html");
		inspiration.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
		inspiration.setAuthLevel(Integer.parseInt(auth_level));
		inspiration.setBriefing(request.getParameter("inspiration_briefing"));
		inspirationAccessCacheRepository.updateInspiration(inspiration);
		
		return "redirect:index";
	}
	
	/**
	 * Manage the content for each inspiration with parameter {inspiration_id}
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/management/edit/{inspiration_id}", method = RequestMethod.GET)
	public String inspirationEdit(@PathVariable int inspiration_id, Locale locale, Model model) {
		model.addAttribute("inspiration",inspirationAccessCacheRepository.getInspirationByID(inspiration_id));
		return "inspirationedit";
	}
	
	/**
	 * Manage the tags for creating, deleting in a pool
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
			inspirationAccessCacheRepository.deleteTags(intTagIDs);
		}
		
		if ((tagNameArray!=null)&&
				(tagColorArray!=null)&&
				(tagNameArray.length>0)&&
				(tagColorArray.length>0)&&
				tagNameArray.length == tagColorArray.length){
			for (int i=0;i<tagNameArray.length;i++){
				Tag newTag = new Tag(tagNameArray[i],tagColorArray[i]);
				inspirationAccessCacheRepository.createTag(newTag);
			}
		}
		
		return "tagpool";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/management/tagpool", method = RequestMethod.GET)
	public String tagPool(Model model) {
		model.addAttribute("tags", inspirationAccessCacheRepository.getFullTagPoolModel());
		return "tagpool";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/management/inspirationpool", method = RequestMethod.GET)
	public String inspirationPool(Model model) {
		model.addAttribute("inspirationList",inspirationAccessCacheRepository.getFullInspirationModel());
		return "inspirationpool";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/management/inspirationremove/{inspiration_id}", method = RequestMethod.GET)
	public String inspirationRemove(@PathVariable int inspiration_id, Model model, Locale locale) {
		
		/**
		 * Delete inspiration on the file system
		 */
		
		Inspiration inspiration = inspirationAccessCacheRepository.getInspirationByID(inspiration_id);
		try {
			String folderName_en = messagesource.getMessage("inspiration.webcontents.folder.location", null,Locale.US) + "/" +inspiration.getUuid();
			String folderName_zh = messagesource.getMessage("inspiration.webcontents.folder.location", null,Locale.CHINA) + "/" +inspiration.getUuid();
			FileUtils.deleteDirectory(new File(folderName_en));
			FileUtils.deleteDirectory(new File(folderName_zh));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Delete Inspiration:" + inspiration.getTitle() + "on the file system failed");
			e.printStackTrace(System.err);
		}
		
		/**
		 *  Delete inspiration
		 */
		inspirationAccessCacheRepository.deleteInspiration(inspiration_id);

		model.addAttribute("inspirationList",inspirationAccessCacheRepository.getFullInspirationModel());
		return "inspirationpool";
	}
	
	/**
	 * Manage the tags for creating, deleting
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
		inspirationAccessCacheRepository.setInspirationUpdated();
		return "management";
	}
	
	
	/**
	 * Selects the contact view to render by returning its name.
	 */
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact(Locale locale, Model model) {
		
		return "contact";
	}
	
	/**
	 * Selects the map view to render by returning its name.
	 */
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String map(Locale locale, Model model) {
		
		return "map";
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
