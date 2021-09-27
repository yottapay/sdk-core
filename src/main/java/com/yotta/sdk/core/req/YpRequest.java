package com.yotta.sdk.core.req;

public interface YpRequest<T_IN, T_OUT> {
    T_OUT sendRequest(T_IN data);
}
