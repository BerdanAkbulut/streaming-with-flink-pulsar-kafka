package com.berdanakbulut.services.config;

import java.util.Optional;

public class AppConfig {
    public static final String SERVICE_HTTP_URL = "http://pulsar:8080";
    public static final String SERVICE_URL = "pulsar://pulsar:6650";

    public static final String KAFKA_URL = "kafka:9092";

    public static final String CLICK_TOPIC = "click-topic";

    public static final String KAFKA_CLICK_TOPIC = "click";

    public static final Optional<String> token = Optional.empty();
}
