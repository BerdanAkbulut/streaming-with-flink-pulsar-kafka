package com.berdanakbulut.services;

import com.berdanakbulut.services.models.ClickEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.function.BiConsumer;

@Service
@Slf4j
public class EventService {

    @Autowired
    private Producer<ClickEvent> clickEventProducer;
    public void produceMessage(String key, ClickEvent event) {
        clickEventProducer
                .newMessage()
                .key(key)
                .value(event)
                .eventTime(System.currentTimeMillis())
                .sendAsync()
                .whenComplete(callback());
    }

    private BiConsumer<MessageId, Throwable> callback() {
        return (id, exception) -> {
            if (exception != null) {
                log.error("❌ Failed message: {}", exception.getMessage());
            } else {
                log.info("✅ Acked message {}", id);
            }
        };
    }
}
