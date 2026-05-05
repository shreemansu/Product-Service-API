package com.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.ApiResponse;
import com.product.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/auth")
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> authenticateCredentialController(@RequestParam String username,@RequestParam String password){
		String serviceResponse=authService.authUserNamePasswordService(username, password);
		ApiResponse response=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("string")
				.payload(serviceResponse)
				.build();
		return ResponseEntity.ok(response);
	}
	@PostMapping("logout")
	public ResponseEntity<ApiResponse> logOutController(HttpServletRequest request){
		String serviceResponse=authService.logOutService(request);
		ApiResponse response=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("string")
				.payload(serviceResponse)
				.build();
		return ResponseEntity.ok(response); 
	}
}
