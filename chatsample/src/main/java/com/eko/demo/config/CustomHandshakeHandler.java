package com.eko.demo.config;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.eko.demo.model.AnonymousPrincipal;

// необходим для хранения в списке конектов, можно разширить стандартный Principal обьект
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
	@Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler, Map<String, Object> attributes) {
	    Principal principal = request.getPrincipal();

        if (principal == null) {
            principal = new AnonymousPrincipal();
            attributes.get(""); // атрибуты рукопожатия
            String uniqueName = UUID.randomUUID().toString();

            ((AnonymousPrincipal) principal).setName(uniqueName);
        }
        return principal;
    }
}
