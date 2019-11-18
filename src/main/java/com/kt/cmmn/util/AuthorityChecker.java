package com.kt.cmmn.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class AuthorityChecker {

	private final OAuth2Authentication principal;
	private final HttpServletRequest request;
	private boolean isAdmin = false;
	@Getter
	private String remoteAddr = null;

	public AuthorityChecker() {
		Object auth = SecurityContextHolder.getContext().getAuthentication();
		if ( OAuth2Authentication.class.isAssignableFrom(auth.getClass()) )
			this.principal = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		else
			this.principal = null;
		this.request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		this.remoteAddr = this.request.getHeader("X-FORWARDED-FOR");
		if ( this.remoteAddr == null )
			this.remoteAddr = this.request.getRemoteAddr();

		if ( "0:0:0:0:0:0:0:1".equals(this.remoteAddr) )
			this.remoteAddr = "127.0.0.1";

		this.initialize();
	}
	
	private void initialize() {
		if ( this.principal == null ) return;
		Collection<GrantedAuthority> c = this.principal.getAuthorities();
		GrantedAuthority[] authorities = new GrantedAuthority[c.size()];
		authorities = c.toArray(authorities);
		//System.out.println(principal);
		for (GrantedAuthority authority : authorities) {
			if ( authority.getAuthority().equals("ROLE_ADMIN") )
				this.isAdmin = true;
		}
	}

	public String getName() {
		if ( this.principal != null )
			return this.principal.getName();
		else
			return null;
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}

}

