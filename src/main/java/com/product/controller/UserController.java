package com.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.AddUserDto;
import com.product.dto.ApiResponse;
import com.product.dto.EmailOtpVerifyDto;
import com.product.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("registration")
	public ResponseEntity<ApiResponse> initiateUserVerification(@RequestBody AddUserDto dto){ 
		String serviceResponse=userService.initiateUserVerification(dto);
		ApiResponse apiResponse=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("string")
				.payload(serviceResponse)
				.build();
		return ResponseEntity.ok(apiResponse);
	}
	@PostMapping("/verification")
	public ResponseEntity<ApiResponse> finalUserVerificationController(@RequestBody EmailOtpVerifyDto dto){
		String serviceResponse=userService.finalUserVerificationService(dto);
		ApiResponse apiResponse=ApiResponse.builder()
				.serviceName("PRODUCT_SERVICE")
				.status(true)
				.type("string")
				.payload(serviceResponse)
				.build();
		return ResponseEntity.ok(apiResponse);
	}
}
