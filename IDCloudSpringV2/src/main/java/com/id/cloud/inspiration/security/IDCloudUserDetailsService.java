package com.id.cloud.inspiration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.id.cloud.inspiration.dao.UserDao;
import com.id.cloud.inspiration.dao.UserRoleDao;
import com.id.cloud.inspiration.entities.User;

@Service("idcloudUserDetailsService")
public class IDCloudUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired 
	private UserRoleDao userRoleDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("UserName "+username+" not found");
		}
		user.setUserRoles(userRoleDao.findByUserIDs(new String[]{user.getUsername()}));
		return new IDCloudSecurityUser(user);
	}
}
