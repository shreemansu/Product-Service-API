package com.product.util;

import org.springframework.stereotype.Component;

@Component
public class EmailMessageBuilderUtil {

	public String otpMessageBuilder(String name, Integer otp) {
		StringBuilder message=new StringBuilder();
		message.append("Dear "+name+"\n");
		message.append("Thank You For Your Interest In XYZ Delivery Services.\n");
		message.append("For Your Account Registration The 6 Digit Otp Is Here :"+otp);
		message.append("\nNote : OTP Valid Only For One Minute");
		return message.toString();
	}
	
	public String userRegisterMessageBuilderUtil(String name) {
		StringBuilder message=new StringBuilder();
		message.append("Dear "+name+",\n");
		message.append("Thanks for your interst in our services, XYZ Delivery.\n");
		message.append("You have been register with us.\n");
		message.append("Note: This ia a system generated email. Do not reply\n");
		return message.toString();
	}
}
