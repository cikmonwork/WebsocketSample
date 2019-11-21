package com.eko.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


// компонент прослушивания конектов и дисконектов, основная задача - отлавливания дисконека
@Component
public class JWebSocketEvent {

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        //получчаем id клиента, чтоб в будущем сложить в мапу и работать с бд
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("connection: " + sha.getSessionId());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("disconnection: " + sha.getSessionId());
    }

}
