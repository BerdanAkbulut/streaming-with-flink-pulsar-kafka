package com.berdanakbulut.services.config.clients;

import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.AuthenticationToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class PulsarClientConfig {
    @Bean
    public PulsarClient initPulsarClient(Optional<String> authToken) throws PulsarClientException {
        ClientBuilder builder = PulsarClient
                .builder()
                .serviceUrl("pulsar://localhost:6650");

        authToken.ifPresent(token -> builder.authentication(new AuthenticationToken(token)));
        return builder.build();
    }
}
