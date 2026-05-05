package com.product.config;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemClassConfig {
	
	@Bean("random")
	public Random creaRandomBean() {
		return new Random();
	}
	
	@Bean("otpholder")
	public Map<String, Object[]> getUserVerificationMap(){
		ConcurrentHashMap<String, Object[]> map=new ConcurrentHashMap<String, Object[]>();
		return map;
	}
	@Bean("invalidjwt")
	public Set<String> createInvalidJWT(){
		return Collections.newSetFromMap(new ConcurrentHashMap<>());
	}
}
