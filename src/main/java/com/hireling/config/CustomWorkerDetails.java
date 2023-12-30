package com.hireling.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hireling.entites.Worker;

public class CustomWorkerDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Worker worker;
	
	
	public CustomWorkerDetails(Worker worker) {
		super();
		this.worker = worker;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority  simpleGrantedAuthority=new SimpleGrantedAuthority(worker.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {	
		return worker.getPassword();
	}

	@Override
	public String getUsername() {
		return worker.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
