package com.product.service;

import com.product.dto.AddUserDto;
import com.product.dto.EmailOtpVerifyDto;

public interface UserService {
	
	public String initiateUserVerification(AddUserDto dto);
	
	public String finalUserVerificationService(EmailOtpVerifyDto dto);
	
	
}
