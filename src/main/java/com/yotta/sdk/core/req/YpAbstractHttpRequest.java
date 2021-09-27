package com.yotta.sdk.core.req;

import com.yotta.sdk.core.domain.YpServiceResponse;
import com.yotta.sdk.core.exception.YpServiceResponseException;
import com.yotta.sdk.core.service.YpCloseableHttpClientSupplierService;
import com.yotta.sdk.core.service.YpObjectMapperService;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class YpAbstractHttpRequest<T_IN, T_OUT> implements YpRequest<T_IN, T_OUT> {
    private static final String HEADER_USER_AGENT_NAME = "User-Agent";

    private final String url;
    private final YpObjectMapperService objectMapperService;
    private final YpCloseableHttpClientSupplierService httpClientSupplier;

    public YpAbstractHttpRequest(
            @NotNull String url,
            @NotNull YpObjectMapperService objectMapperService,
            @NotNull YpCloseableHttpClientSupplierService httpClientSupplier) {
        this.url = url;
        this.objectMapperService = objectMapperService;
        this.httpClientSupplier = httpClientSupplier;
    }

    @Override
    public T_OUT sendRequest(T_IN data) {
        try (CloseableHttpClient httpClient = httpClientSupplier.get()) {
            HttpRequestBase request = createRequestObject(data);
            addHeaders(request, data);
            if (request instanceof HttpEntityEnclosingRequestBase) {
                HttpEntityEnclosingRequestBase entityRequest = (HttpEntityEnclosingRequestBase) request;
                setRequestBody(entityRequest, data);
            }
            YpServiceResponse serviceResponse = executeRequest(httpClient, request);
            return processResponse(data, serviceResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String getSdkName();

    protected YpObjectMapperService getObjectMapperService() {
        return objectMapperService;
    }

    protected abstract HttpRequestBase createRequestObject(T_IN input);

    protected HttpGet createEmptyGetRequest(T_IN input) {
        return new HttpGet(getUrl());
    }

    protected HttpPost createEmptyPostRequest(T_IN input) {
        return new HttpPost(getUrl());
    }

    protected void addHeaders(HttpRequestBase request, T_IN input) {
        request.addHeader(HEADER_USER_AGENT_NAME, getSdkName());
    }

    protected void setRequestBody(HttpEntityEnclosingRequestBase request, T_IN input) {
        Object body = convertToRequestBody(input);
        if (body == null) {
            return;
        }

        Object requestObject = convertToRequestBody(input);
        String json = convertObjectToJson(requestObject);
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
    }

    protected Object convertToRequestBody(T_IN input) {
        return input;
    }

    protected String convertObjectToJson(Object object) {
        return objectMapperService.objectToJson(object);
    }

    protected <T> T convertJsonToObject(@NotNull String json, @NotNull Class<T> targetClass) {
        return objectMapperService.jsonToObject(json, targetClass);
    }

    protected String getUrl() {
        return url;
    }

    protected abstract T_OUT onOk(T_IN input, YpServiceResponse response) throws YpServiceResponseException;

    protected T_OUT onForbidden(T_IN input, YpServiceResponse response) throws YpServiceResponseException {
        throw new YpServiceResponseException(response);
    }

    protected T_OUT onNotFound(T_IN input, YpServiceResponse response) throws YpServiceResponseException {
        throw new YpServiceResponseException(response);
    }

    protected T_OUT onInternalServerError(T_IN input, YpServiceResponse response) throws YpServiceResponseException {
        throw new YpServiceResponseException(response);
    }

    protected T_OUT onOtherStatus(T_IN input, YpServiceResponse response) throws YpServiceResponseException {
        throw new YpServiceResponseException(response);
    }

    protected YpServiceResponse executeRequest(CloseableHttpClient client, HttpRequestBase request) {
        try (CloseableHttpResponse response = client.execute(request)) {
            return YpServiceResponse.fromHttpResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected T_OUT processResponse(T_IN input, YpServiceResponse response) throws YpServiceResponseException {
        if (response.isOk()) {
            return onOk(input, response);
        } else if (response.isForbidden()) {
            return onForbidden(input, response);
        } else if (response.isNotFound()) {
            return onNotFound(input, response);
        } else if (response.isInternalServerError()) {
            return onInternalServerError(input, response);
        }

        return onOtherStatus(input, response);
    }
}
