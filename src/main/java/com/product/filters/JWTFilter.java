package com.product.filters;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.product.util.JWTutil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter{
	
	private final JWTutil jwtUtil;
	
	@Qualifier("invalidjwt")
	private final Set<String> blockedjwt;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

	    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
	    String jwt = null;

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        jwt = authHeader.substring(7);
	    }

	    if (jwt == null) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    try {
	        if (jwtUtil.isTokenExpired(jwt) || blockedjwt.contains(jwt)) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.setContentType("application/json");
	            response.getWriter().write("{\"message\":\"JWT expired\"}");
	            return;
	        }
	    } catch (Exception e) {
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType("application/json");
	        response.getWriter().write("{\"message\":\"JWT invalid\"}");
	        return;
	    }

	    filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    String path = request.getRequestURI();
	    return path.startsWith("/api/v3/auth/login");
	}
}