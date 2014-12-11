package com.id.cloud.inspiration.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.id.cloud.inspiration.entities.User;
import com.id.cloud.inspiration.entities.UserRole;

public class IDCloudSecurityUser extends User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IDCloudSecurityUser(User user) {
		if(user != null)
		{
			this.setId(user.getId());
			this.setEnabled((user.isEnabled()));
			this.setUsername(user.getUsername());
			this.setPassword(user.getPassword());
			this.setNickname(user.getNickname());
			this.setUserRoles(user.getUserRoles());
		}      
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<UserRole> userRoles = this.getUserRoles();
		if(userRoles != null)
		{
			for (UserRole role : userRoles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
				authorities.add(authority);
			}
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

}
