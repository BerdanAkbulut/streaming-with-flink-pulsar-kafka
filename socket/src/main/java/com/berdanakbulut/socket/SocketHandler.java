package com.berdanakbulut.socket;

import com.berdanakbulut.services.EventKafkaService;
import com.berdanakbulut.services.EventService;
import com.berdanakbulut.services.models.ClickEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.UUID;

@Slf4j
@Component
@ComponentScan({"com.berdanakbulut.socket", "com.berdanakbulut.services"})
@EntityScan(basePackages = "com.berdanakbulut.services")
public class SocketHandler extends TextWebSocketHandler {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventKafkaService eventKafkaService;

    @Autowired
    private ObjectMapper mapper;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("Message received: {}", message.toString());
        ClickEvent clickEvent;
        try {
            clickEvent = mapper.readValue(message.getPayload(), ClickEvent.class);
        } catch (Exception e) {
            log.debug("Exception when converting event to ClickEvent model. Exception: {}", e.toString());
            return;
        }

        eventKafkaService.sendMessage(UUID.randomUUID().toString(), clickEvent);
        eventService.produceMessage(clickEvent.getText(), clickEvent); // to apache pulsar
    }
}
