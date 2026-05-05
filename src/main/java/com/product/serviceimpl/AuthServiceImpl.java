package com.product.serviceimpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.product.service.AuthService;
import com.product.util.JWTutil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final AuthenticationManager authManager;
	
	private final JWTutil jwTutil;
	
	@Qualifier("invalidjwt")
	private final Set<String> blockedjwt;
	
	@Override
	public String authUserNamePasswordService(String username, String password) {
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication=authManager.authenticate(token);
		if(authentication.isAuthenticated()) {
			List<String> roles=authentication.getAuthorities()
					.stream()
					.map(authority->authority.getAuthority()).toList();
			String jwt=jwTutil.createJwtToken(username, roles);
			return jwt;
		}
		throw new RuntimeException("Invalid Password");
	}

	@Override
	public String logOutService(HttpServletRequest request) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	    String jwt = null;
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        jwt = authHeader.substring(7);
	    }
	    if(blockedjwt.contains(jwt)) return "Already Log Out";
	    blockedjwt.add(jwt);
		return "You have been logged out successfully";
	}

}
