package com.id.cloud.web.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.id.cloud.delicacy.dao.DelicacyDao;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/delicacy")
public class DelicacyController {
	
	private static final Logger logger = LoggerFactory.getLogger(DelicacyController.class);
	
	@Autowired
	private DelicacyDao delicacyDao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		return "index";
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
		
		/*Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
		String formattedDate = dateFormat.format(date);		
		model.addAttribute("serverTime", formattedDate );
		Delicacy delicacy = new Delicacy();
		delicacy.setDelicacyHighlight("No highlight");
		delicacy.setDelicacyName("test2");
		delicacy.setDelicacyStory("No Story");
		int returnedKey = delicacyDao.create(delicacy);
		System.out.println("returnedKey= " + returnedKey);
		delicacy.setDelicacyName("test update");
		delicacy.setDelicacyID(6);
		delicacyDao.update(delicacy);*/
		
		
/*		Delicacy delicacy = new Delicacy();
		delicacy.setDelicacyMultimedia("");
		delicacy.setDelicacyName("Chiffon Cake");
		delicacy.setDelicacyStory("No Story");
		
		ArrayList<DelicacyStep> stepList = new  ArrayList<DelicacyStep>();
		for (int i=1; i<10;i++){
			DelicacyStep delicacyStep = new DelicacyStep();
			delicacyStep.setDelicacy(delicacy);
			delicacyStep.setDelicacyStep_Num(i);
			delicacyStep.setStepDescription("good step" + i);
			delicacyStep.setStepMultimedia("");
			delicacyStep.setStepName("step" + i);
			delicacyStep.setStepTime(60*i);
			ArrayList<DelicacyCooktool> cookToolList = new  ArrayList<DelicacyCooktool>();
			for (int j=1;j<3;j++){
				DelicacyCooktool cookTool = new DelicacyCooktool();
				cookTool.setCookToolBrand("niuniu");
				cookTool.setCookToolDescription("good cooktool "+j);
				cookTool.setCookToolName("niu "+j);
				cookTool.setCookToolURL(" ");
				cookTool.setDelicacyStep(delicacyStep);
				cookToolList.add(cookTool);
			}
			delicacyStep.setCookTools(cookToolList);
			stepList.add(delicacyStep);
		}
		delicacy.setDelicacySteps(stepList);
		delicacyService.saveOrUpdateDelicacy(delicacy);*/
		
		return "dataAccessFailure";
	}
	
}
