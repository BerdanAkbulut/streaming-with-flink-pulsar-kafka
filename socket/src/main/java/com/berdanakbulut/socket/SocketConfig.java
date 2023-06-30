package com.berdanakbulut.socket;

import com.berdanakbulut.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@ComponentScan({"com.berdanakbulut.socket", "com.berdanakbulut.services"})
@EntityScan(basePackages = "com.berdanakbulut.services")
public class SocketConfig implements WebSocketConfigurer {

    @Autowired
    private SocketHandler socketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/stream").setAllowedOrigins("*");
    }
}
