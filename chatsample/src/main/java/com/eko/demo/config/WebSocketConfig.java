package com.eko.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// класс конфигурации вебсокетов
@Configuration
@EnableWebSocketMessageBroker // вкл брокер сообщений websocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
        // адреса получения клиентом сообщений
		config.enableSimpleBroker("/topic/", "/queue/");

		// адрес для получения сообщения от клиента
		config.setApplicationDestinationPrefixes("/app");
	}

	// адрес вебсокетов с установкой объекта хранения информации об соеднении
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/greeting").setHandshakeHandler(new CustomHandshakeHandler());
	}
}
