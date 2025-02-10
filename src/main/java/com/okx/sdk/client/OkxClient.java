package com.okx.sdk.client;

import com.okx.sdk.config.OkxConfig;
import lombok.Getter;

/**
 * OKX API V5 客户端
 */
@Getter
public class OkxClient {
    private final String apiKey;
    private final String secretKey;
    private final String passphrase;
    private final String baseUrl;

    public OkxClient(String apiKey, String secretKey, String passphrase) {
        this(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);
    }

    public OkxClient(String apiKey, String secretKey, String passphrase, String baseUrl) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
        this.baseUrl = baseUrl;
    }
} 