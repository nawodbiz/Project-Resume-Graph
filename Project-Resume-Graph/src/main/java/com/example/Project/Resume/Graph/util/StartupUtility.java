package com.example.Project.Resume.Graph.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class StartupUtility implements CommandLineRunner {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void run(String... args) throws Exception {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}