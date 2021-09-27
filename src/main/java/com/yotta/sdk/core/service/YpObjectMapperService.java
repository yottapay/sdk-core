package com.yotta.sdk.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yotta.sdk.core.service.impl.YpObjectMapperServiceImpl;

public interface YpObjectMapperService {

    YpObjectMapperService DEFAULT = new YpObjectMapperServiceImpl(new ObjectMapper());

    String objectToJson(Object object);

    <T> T jsonToObject(String json, Class<T> targetType);
}
