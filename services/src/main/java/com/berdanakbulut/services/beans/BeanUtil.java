package com.berdanakbulut.services.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
