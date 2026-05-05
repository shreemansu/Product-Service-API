package com.product.serviceimpl;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.product.dto.AddUserDto;
import com.product.dto.EmailOtpVerifyDto;
import com.product.entity.User;
import com.product.events.SimpleMessageEvent;
import com.product.mapping.ModelMapper;
import com.product.repository.UserRepository;
import com.product.service.MailService;
import com.product.service.UserService;
import com.product.util.EmailMessageBuilderUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final EmailMessageBuilderUtil emailMessageBuilder;
	
	private final Random random;
	
	private final MailService mailService; 
	
	@Autowired
	@Qualifier("otpholder")
	private Map<String, Object[]> otpHolder;
	
	private final ApplicationEventPublisher eventPublisher;
	
	private final ModelMapper modelMapper;
	
	private final UserRepository userRepo;
	
	@Override
	public String initiateUserVerification(AddUserDto dto) {
		Integer otp=random.nextInt(100000, 999999);
		String otpMessage=emailMessageBuilder.otpMessageBuilder(dto.getName(), otp);
		//mailService.sentEmail(dto.getEmail(), otpMessage);
		SimpleMessageEvent emailEvent=SimpleMessageEvent.builder()
				.receiverEmail(dto.getEmail())
				.message(otpMessage)
				.subject("Products Alert : OTP ")
				.build();
		eventPublisher.publishEvent(emailEvent);
		Object[] tempOtpData= {otp+"", LocalDateTime.now().plusMinutes(2), dto};
		otpHolder.put(dto.getEmail(), tempOtpData);
//		for(Entry<String, Object[]> e:otpHolder.entrySet()) {
//			System.out.println(e);
//		}
		return "OTP Sent to Email "+dto.getEmail();
	}

	@Override
	public String finalUserVerificationService(EmailOtpVerifyDto dto) {
		Object[] tempUserData=otpHolder.get(dto.getEmail());
		if(tempUserData==null) throw new RuntimeException("Invalid Email id");
		LocalDateTime currDateTime=LocalDateTime.now();
		LocalDateTime expiryDateTime=(LocalDateTime)tempUserData[1];
		if(currDateTime.isAfter(expiryDateTime)) throw new RuntimeException("Time Over, Use Keypad");
		String inMemoryOtp=(String)tempUserData[0];
		String userOtp=dto.getOtp();
		if(!inMemoryOtp.equals(userOtp)) throw new RuntimeException("Invalid OTP");
		//TODO get user Object from Dto, Save The Object in DB
		AddUserDto userDto=(AddUserDto)tempUserData[2];
		User user=modelMapper.addUserDtoToUserEntity(userDto); //(AddUserDto)tempUserData[2]
		userRepo.save(user);
		String message=emailMessageBuilder.userRegisterMessageBuilderUtil(userDto.getName());
		SimpleMessageEvent event=SimpleMessageEvent.builder()
				.receiverEmail(dto.getEmail())
				.message(message)
				.subject("Alert!: User Registered Successfully")
				.build();
		eventPublisher.publishEvent(event);
		return "User Saved"; 
	}
	
	
}
