package com.ksawio.fitnessapi.test_utils;

import org.springframework.core.io.ClassPathResource;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public final class LoadTestData {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> List<T> loadListData(String file, Class<T> collectionType) throws IOException {
        return objectMapper.readValue(
                new ClassPathResource(file).getInputStream(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, collectionType));
    }
}
