package com.yotta.sdk.core.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yotta.sdk.core.service.YpObjectMapperService;

public class YpObjectMapperServiceImpl implements YpObjectMapperService {
    private final ObjectMapper objectMapper;

    public YpObjectMapperServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T jsonToObject(String json, Class<T> targetType) {
        try {
            return objectMapper.readValue(json, targetType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
