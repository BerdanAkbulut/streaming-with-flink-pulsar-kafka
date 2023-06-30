package com.berdanakbulut.services.config.producers;

import com.berdanakbulut.services.models.ClickEvent;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClickProducerConfig {

    @Autowired
    private PulsarClient pulsarClient;

    @Bean
    protected Producer<ClickEvent> pulsarProducer() throws PulsarClientException {
        return pulsarClient.newProducer(JSONSchema.of(ClickEvent.class))
                .producerName("click-producer")
                .topic("click-topic")
                .blockIfQueueFull(true)
                .create();
    }
}
