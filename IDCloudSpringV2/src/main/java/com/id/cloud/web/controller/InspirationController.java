package com.id.cloud.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
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
import org.springframework.core.env.Environment;
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
import com.id.cloud.inspiration.dao.InspirationDao;
import com.id.cloud.inspiration.dao.InspirationM2MTagDao;
import com.id.cloud.inspiration.dao.TagDao;
import com.id.cloud.inspiration.dao.UserDao;
import com.id.cloud.inspiration.entities.AccessLog;
import com.id.cloud.inspiration.entities.Inspiration;
import com.id.cloud.inspiration.entities.InspirationM2MTag;
import com.id.cloud.inspiration.entities.Tag;
import com.id.cloud.inspiration.entities.User;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/inspiration")
public class InspirationController {
	
	private static final Logger logger = LoggerFactory.getLogger(InspirationController.class);
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private InspirationDao inspirationDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private InspirationM2MTagDao inspirationM2MTagDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccessLogDao accessLogDao;
	
	/**
	 * Query for the inspirations and related tags and display them on the index page
	 * Selects the index view to render by returning its name.
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String inspirationIndex(Locale locale, Model model, HttpServletRequest request) {
		final String userIpAddress = request.getRemoteAddr();
        final String userAgent = request.getHeader("user-agent");
        final String user = request.getRemoteUser();
        accessLogDao.create(new AccessLog(userIpAddress, userAgent, "Inspiration Index",user));
		
		List<Inspiration> inspirationList = inspirationDao.findAll();
		for(int i=0 ; i< inspirationList.size();i++){
			List<InspirationM2MTag> inspirationM2MTags = inspirationM2MTagDao.findByInspirationID(inspirationList.get(i).getInspirationID());
			Integer [] tagIDs = new Integer[inspirationM2MTags.size()];
			for (int j=0 ; j<inspirationM2MTags.size() ; j++) 
			{
				tagIDs[j] = inspirationM2MTags.get(j).getTagID();
			}
			inspirationList.get(i).setTags(tagDao.findByTagIDs(tagIDs));
			User author = userDao.findByPrimaryKey(inspirationList.get(i).getAuthor());
			inspirationList.get(i).setAuthorNickname(author.getNickname());
		}
		model.addAttribute("inspirationList",inspirationList);
		return "index";
	}
	
	/**
	 * Publish the inspiration and associate the tags to it
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
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
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public String inspirationPost(Locale locale, Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		
		/*
		 * Create inspiration on the file system.
		 */
		String uuid = UUID.randomUUID().toString();
		String inspirationTitle = request.getParameter("inspiration_title");
		String folderName = environment.getProperty("inspiration.webcontents.folder.location")+uuid;
		String fileName = folderName+"/"+uuid+".html";
		
		(new File(folderName)).mkdirs();
		
		try{
			FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
			osw.write(request.getParameter("inspiration_editor"));
			osw.flush();
			osw.close();
		}
		catch(IOException e){
			
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
		return "inspirationtag";
	}
	
	/**
	 * Direct link for getting the content of each inspiration with parameter {inspiration_id}
	 */
	@RequestMapping(value = "/{inspiration_id}", method = RequestMethod.GET)
	public String directLink(@PathVariable String inspiration_id, Locale locale, Model model) {
		int inspirationID = Integer.parseInt(inspiration_id);
		Inspiration inspiration = inspirationDao.findByPrimaryKey(inspirationID);
		inspiration.setAuthorNickname(userDao.findByPrimaryKey(inspiration.getAuthor()).getNickname());
		
		List<InspirationM2MTag> inspirationM2MTags = inspirationM2MTagDao.findByInspirationID(inspirationID);
		Integer [] tagIDs = new Integer[inspirationM2MTags.size()];
		for (int i=0 ; i<inspirationM2MTags.size() ; i++) 
		{
			tagIDs[i] = inspirationM2MTags.get(i).getTagID();
		}
		inspiration.setTags(tagDao.findByTagIDs(tagIDs));
		
		model.addAttribute("inspiration",inspiration);
				
		/*
		 * Read inspiration on the file system.
		 */
		
		String folderName = environment.getProperty("inspiration.webcontents.folder.location")+inspiration.getUuid();
		String fileName = folderName+"/"+inspiration.getUuid()+".html";
		
		try{
			Path paths = Paths.get(fileName);
			byte[] encoded = Files.readAllBytes(paths);
			String inspirationString = new String(encoded, "UTF-8");
			model.addAttribute("inspirationString",inspirationString);
			
		}
		catch(IOException e){
			
		}
		
		return "directlink";
	}
	
	/**
	 * Manage the content for each inspiration with parameter {inspiration_id}
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/{inspiration_id}", method = RequestMethod.POST)
	public String inspirationEditPost(@PathVariable String inspiration_id, Locale locale, Model model, HttpServletRequest request) {
		
		
		Inspiration inspiration = inspirationDao.findByPrimaryKey(Integer.parseInt(inspiration_id));
	
		
		String article_update = request.getParameter("inspiration_article_update"); 
		String inspirationTitle = request.getParameter("inspiration_title-edit");
		String uuid = inspiration.getUuid();
		
		if("update".equals(article_update)){
			
			/**
			 * Write edited inspiration on the file system.
			 */
			String folderName = environment.getProperty("inspiration.webcontents.folder.location")+uuid;
			String fileName = folderName+"/"+uuid+".html";
			
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
		inspirationDao.update(inspiration);
		
		return "redirect:index";
	}
	
	/**
	 * Manage the content for each inspiration with parameter {inspiration_id}
	 * Selects the publish view to render by returning its name
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/management/edit/{inspiration_id}", method = RequestMethod.GET)
	public String inspirationEdit(@PathVariable String inspiration_id, Locale locale, Model model) {
		Inspiration inspiration = inspirationDao.findByPrimaryKey(Integer.parseInt(inspiration_id));
		model.addAttribute("inspiration",inspiration);
		
		
		/*
		 * No need to read inspiration on the file system.
		 */
		
		/*String folderName = environment.getProperty("inspiration.webcontents.folder.location")+inspiration.getUuid();
		String fileName = folderName+"/"+inspiration.getUuid()+".html";
		
		try{
			Path paths = Paths.get(fileName);
			byte[] encoded = Files.readAllBytes(paths);
			String inspirationString = new String(encoded, "UTF-8");
			model.addAttribute("inspirationString",inspirationString);
			
		}
		catch(IOException e){
			
		}*/
		
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/management/inspirationremove/{inspiration_id}", method = RequestMethod.GET)
	public String inspirationRemove(@PathVariable String inspiration_id, Model model) {
		int inspirationID = Integer.parseInt(inspiration_id);
		
		/**
		 * Delete inspiration on the file system
		 */
		
		Inspiration inspiration = inspirationDao.findByPrimaryKey(inspirationID);
		try {
		String folderName = environment.getProperty("inspiration.webcontents.folder.location") + inspiration.getUuid();
		
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
		
		/**
		 * Delete inspiration-tag relation
		 */
		inspirationM2MTagDao.deleteByInspirationID(inspirationID);
		
		List<Inspiration> inspirationList = inspirationDao.findAll();
		model.addAttribute("inspirationList",inspirationList);
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
