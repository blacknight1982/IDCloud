package com.id.cloud.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.id.cloud.inspiration.dao.TagDao;
import com.id.cloud.inspiration.entities.IPLocationInfo;
import com.id.cloud.inspiration.entities.Inspiration;
import com.id.cloud.inspiration.entities.Tag;
import com.id.cloud.inspiration.repository.InspirationAccessCacheRepository;
import com.id.cloud.inspiration.repository.InspirationAccessLocationRepository;
import com.id.cloud.inspiration.utility.imageupload.RestResponse;
import com.id.cloud.inspiration.utility.webshare.WebShare;
import com.id.cloud.inspiration.utility.webshare.WebShareRepository;

/**
 * Handles requests as RESTful web service and return with JSON format
 */
@Controller
@RequestMapping("/inspiration/rest")
public class RESTfulController {
	
	@Autowired
	private AbstractMessageSource messagesource;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private WebShareRepository webShareRepository;
	
	@Autowired
	private InspirationAccessCacheRepository inspirationAccessCacheRepository;
	
	@Autowired
	private InspirationAccessLocationRepository inspirationAccessLogRepository;

/*	@RequestMapping(method = RequestMethod.GET, value = "/inspiration/{inspirationID}", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Inspiration> getInspiration(@PathVariable int inspirationID){
		Inspiration inspiration = inspirationDao.findByPrimaryKey(inspirationID);
		inspiration.setAuthorNickname(userDao.findByUsername(inspiration.getAuthor()).getNickname());
		return new ResponseEntity<Inspiration>(inspiration, HttpStatus.OK);
	}*/
	
	@RequestMapping(method = RequestMethod.POST, value = "/webshare", consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Void> postWebShare(@RequestBody Map<String,String> body){
		String shareContent = body.get("shareContent");
		webShareRepository.create(shareContent);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/webshare", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<WebShare> getWebShare(){
		
		return new ResponseEntity<WebShare>(webShareRepository.getWebShare(),HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/imageupload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<RestResponse> getRestResponse(@RequestParam("image") final MultipartFile multiPart, Locale locale){
		String fileName = null;
		String folderName = messagesource.getMessage("inspiration.imageupload.folder.location", null,locale);
		String weblocation = messagesource.getMessage("inspiration.imageupload.url.location", null,locale);
				
    	if (!multiPart.isEmpty()) {
            try {
                fileName = UUID.randomUUID()+"-"+multiPart.getOriginalFilename();   
                byte[] bytes = multiPart.getBytes();
                BufferedOutputStream buffStream = 
                        new BufferedOutputStream(new FileOutputStream(new File(folderName + "/" +fileName)));
                buffStream.write(bytes);
                buffStream.close();
            } catch (Exception e) {
                
            }
        } else {
            
        }
    	RestResponse restResponse = new RestResponse();
    	restResponse.getUpload().getLinks().setOriginal(weblocation+"/"+fileName);
    	restResponse.getUpload().getImage().setWidth(300);
		return new ResponseEntity<RestResponse>(restResponse,HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/tag", consumes = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<Void> postTag(@RequestBody Map<String,String> tag){
		Tag newTag = new Tag(tag.get("name"),tag.get("color"));
		tagDao.create(newTag);
		inspirationAccessCacheRepository.setTagpoolUpdated();
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/inspiration", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<Inspiration>> getInspirations(){	
		return new ResponseEntity<List<Inspiration>>(inspirationAccessCacheRepository.getInspirationModelByAuthorization(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/accesslocationstoday", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<IPLocationInfo>> getAccessLocationsForToday(){	
		return new ResponseEntity<List<IPLocationInfo>>(inspirationAccessLogRepository.getAccessIPLocationForToday(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/accesslocations", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<IPLocationInfo>> getAccessLocations(){	
		return new ResponseEntity<List<IPLocationInfo>>(inspirationAccessLogRepository.getAllAccessIPLocations(), HttpStatus.OK);
	}
}
