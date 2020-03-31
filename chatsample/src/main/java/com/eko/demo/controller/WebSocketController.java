package com.eko.demo.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;

@Controller
public class WebSocketController  {
    // сервис для отправок сообщений
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/usermessage/{userId}") // отправка клиентом сообщения приватно
	public void processMessageFromClient(@Payload String message, Principal principal,
										 @DestinationVariable String userId,
										 SimpMessageHeaderAccessor headerAccessor) throws Exception {
		System.out.println("processMessageFromClient");
		System.out.println(message+userId);
		System.out.println("simpl"+headerAccessor.toString());
		System.out.println(principal.getName());
		 String name = new Gson().fromJson(message, Map.class).get("talkto").toString();

		 // отправка сообщения приватно по указанному имени пользоателя
		messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply",principal.getName()+"-"+name);
		messagingTemplate.convertAndSendToUser(principal.getName(), "/vents/one","vents/one "+principal.getName()+"-"+name);
	}


	@MessageMapping("/allmessage") // клиент по этому адрессу отправляет сообщения всем
	public void processallMessageFromClient(@Payload String message, Principal principal) throws Exception {
		System.out.println("processallMessageFromClient");
		System.out.println(message);
		 String name = new Gson().fromJson(message, Map.class).get("name").toString();
		 // отправка сообщения глобально
		messagingTemplate.convertAndSend("/topic/reply",principal.getName()+"-"+name);
	}

	// отправка ошибок
	@MessageExceptionHandler
    public String handleException(Throwable exception) {
		System.out.println("handleException");
		return exception.getMessage();
    }

}
