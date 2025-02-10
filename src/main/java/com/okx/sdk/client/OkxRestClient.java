package com.okx.sdk.client;

import com.alibaba.fastjson2.JSON;
import com.okx.sdk.config.OkxConfig;
import com.okx.sdk.utils.SignatureUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * OKX REST API 基础客户端
 */
@Slf4j
public class OkxRestClient {
    private final OkHttpClient httpClient;
    private final String apiKey;
    private final String secretKey;
    private final String passphrase;
    private final String baseUrl;

    public OkxRestClient(String apiKey, String secretKey, String passphrase, String baseUrl) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
        this.baseUrl = baseUrl;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 执行GET请求
     */
    protected String get(String path, String queryParams) throws IOException {
        String timestamp = SignatureUtils.getTimestamp();
        String fullPath = path + (queryParams == null ? "" : "?" + queryParams);
        String signature = SignatureUtils.sign(timestamp, "GET", fullPath, "", secretKey);

        Request request = new Request.Builder()
                .url(baseUrl + fullPath)
                .get()
                .addHeader("OK-ACCESS-KEY", apiKey)
                .addHeader("OK-ACCESS-SIGN", signature)
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", passphrase)
                .build();

        return executeRequest(request);
    }

    /**
     * 执行POST请求
     */
    protected String post(String path, Object body) throws IOException {
        String timestamp = SignatureUtils.getTimestamp();
        String jsonBody = body == null ? "" : JSON.toJSONString(body);
        String signature = SignatureUtils.sign(timestamp, "POST", path, jsonBody, secretKey);

        RequestBody requestBody = RequestBody.create(
                jsonBody,
                MediaType.parse(OkxConfig.CONTENT_TYPE)
        );

        Request request = new Request.Builder()
                .url(baseUrl + path)
                .post(requestBody)
                .addHeader("OK-ACCESS-KEY", apiKey)
                .addHeader("OK-ACCESS-SIGN", signature)
                .addHeader("OK-ACCESS-TIMESTAMP", timestamp)
                .addHeader("OK-ACCESS-PASSPHRASE", passphrase)
                .addHeader("Content-Type", OkxConfig.CONTENT_TYPE)
                .build();

        return executeRequest(request);
    }

    private String executeRequest(Request request) throws IOException {
        try (Response response = httpClient.newCall(request).execute()) {
            String body = Objects.requireNonNull(response.body()).string();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code() + ", body: " + body);
            }
            return body;
        }
    }
} 