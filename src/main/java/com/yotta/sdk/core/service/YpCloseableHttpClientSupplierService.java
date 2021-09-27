package com.yotta.sdk.core.service;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.function.Supplier;

public interface YpCloseableHttpClientSupplierService extends Supplier<CloseableHttpClient> {
    YpCloseableHttpClientSupplierService DEFAULT = HttpClients::createDefault;
}
