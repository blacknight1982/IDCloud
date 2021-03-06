package com.id.cloud.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.id.cloud.inspiration.dao.UserDao;
import com.id.cloud.inspiration.dao.UserRoleDao;
import com.id.cloud.inspiration.entities.AccountForm;
import com.id.cloud.inspiration.entities.LoginForm;
import com.id.cloud.inspiration.entities.User;
import com.id.cloud.inspiration.entities.UserRole;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AccessController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessController.class);
	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	
	@Autowired
	@Qualifier("idcloudAuthenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	/**
	 * post login Servlet
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result) throws IOException{
		String username = loginForm.getIdcloud_username();
		String password = loginForm.getIdcloud_password();
		
		if(result.hasErrors()){
			return "login";
		}
		
		//use the AuthenticationManager to help with the authentication
		Authentication token = new UsernamePasswordAuthenticationToken(username,password);
		try{
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			successHandler.onAuthenticationSuccess(request, response, authentication);
					
		}catch(AuthenticationException authExp){
			logger.debug(authExp.getMessage());
			result.reject("Login Fail","Authentication Failed");
			return "login";
		}
		catch(ServletException servletExp){
			result.reject("Servlet Exception",servletExp.getMessage());
			return "login";
		}
				
		return null;
	}
	
	/**
	 * login Servlet
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@ModelAttribute("loginForm") LoginForm loginForm){
		return "login";
	}
	
	/**
	 * Create Account Servlet
	 */
	@RequestMapping(value = "/createAccount", method = RequestMethod.GET)
	public String createAccount(@ModelAttribute("accountForm") AccountForm accountForm){
		return "create_account";
	}
	
	/**
	 * Post Create Account Servlet
	 */
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public String createAccount(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("accountForm") AccountForm accountForm, BindingResult result){
		String username = accountForm.getIdcloud_username();
		String password = accountForm.getIdcloud_password();
		String confirm_password = accountForm.getIdcloud_confirm_password();
		String nickname = accountForm.getIdcloud_nickname();
		if(result.hasErrors()){
			return "create_account";
		}
		
		if(!password.equals(confirm_password)){
			result.reject("Create Account Fail","Passwords does not equal.");
			return "create_account";
		}
		User newUser = new User(username,password,true,nickname);
		UserRole newUserRole = new UserRole(username,"ROLE_USER");
		userDao.create(newUser);
		userRoleDao.create(newUserRole);
		
		return "redirect:/login";
	}
	
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String accessDenied(@ModelAttribute("loginForm") LoginForm loginForm){
		return "accessdenied";
	}
}
