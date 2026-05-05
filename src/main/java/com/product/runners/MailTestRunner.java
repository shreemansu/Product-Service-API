package com.product.runners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.product.service.MailService;

import lombok.RequiredArgsConstructor;

//@Component
@RequiredArgsConstructor
public class MailTestRunner implements CommandLineRunner {

	private final MailService mailService;
	
	@Override
	public void run(String... args) throws Exception {
		//mailService.sentEmail("springzillaprime@gmail.com", "Bring it on");
	}
	
}
