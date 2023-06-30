package com.berdanakbulut.services;

import com.berdanakbulut.services.models.ClickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.berdanakbulut.services.config.AppConfig.KAFKA_CLICK_TOPIC;

@Service
public class EventKafkaService {

    @Autowired
    private KafkaTemplate<String, ClickEvent> eventKafkaTemplate;

    public void sendMessage(String key, ClickEvent value) {
        eventKafkaTemplate.send(KAFKA_CLICK_TOPIC, key, value);
    }
}
