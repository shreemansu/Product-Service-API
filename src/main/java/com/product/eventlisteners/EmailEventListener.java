package com.product.eventlisteners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.product.events.SimpleMessageEvent;
import com.product.service.MailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailEventListener {
	
	private final MailService mailService;
	
	@EventListener
	@Async
	public void handleSimpleEmailEvent(SimpleMessageEvent event) {
		mailService.sentEmail(event.getReceiverEmail(), event.getMessage(), event.getSubject());
	}
}
