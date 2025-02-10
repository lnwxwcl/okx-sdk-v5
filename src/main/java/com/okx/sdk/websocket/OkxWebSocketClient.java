package com.okx.sdk.websocket;

import com.alibaba.fastjson2.JSON;
import com.okx.sdk.utils.SignatureUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.ByteString;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OKX WebSocket客户端基类
 */
@Slf4j
public abstract class OkxWebSocketClient {
    private static final int CONNECT_TIMEOUT = 10;
    private static final int PING_INTERVAL = 20;

    protected final String url;
    protected final String apiKey;
    protected final String secretKey;
    protected final String passphrase;
    protected WebSocket webSocket;

    public OkxWebSocketClient(String url, String apiKey, String secretKey, String passphrase) {
        this.url = url;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
    }

    /**
     * 连接WebSocket服务器
     */
    public void connect() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .pingInterval(PING_INTERVAL, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                OkxWebSocketClient.this.webSocket = webSocket;
                log.info("WebSocket connected");
                onConnected();
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                onMessageReceived(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                onMessageReceived(bytes.utf8());
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                log.info("WebSocket closing: {} - {}", code, reason);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                log.info("WebSocket closed: {} - {}", code, reason);
                onDisconnected();
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                log.error("WebSocket failure", t);
                onError(t);
            }
        });
    }

    /**
     * 订阅频道
     *
     * @param channel 频道名称
     * @param args    订阅参数
     */
    protected void subscribe(String channel, Map<String, String> args) {
        Map<String, Object> request = new HashMap<>();
        request.put("op", "subscribe");
        request.put("channel", channel);
        request.put("args", args);

        String message = JSON.toJSONString(request);
        webSocket.send(message);
    }

    /**
     * 取消订阅频道
     *
     * @param channel 频道名称
     * @param args    订阅参数
     */
    protected void unsubscribe(String channel, Map<String, String> args) {
        Map<String, Object> request = new HashMap<>();
        request.put("op", "unsubscribe");
        request.put("channel", channel);
        request.put("args", args);

        String message = JSON.toJSONString(request);
        webSocket.send(message);
    }

    /**
     * 登录
     */
    protected void login() {
        String timestamp = SignatureUtils.getTimestamp();
        String signature = SignatureUtils.sign(timestamp, "GET", "/users/self/verify", "", secretKey);

        Map<String, String> args = new HashMap<>();
        args.put("apiKey", apiKey);
        args.put("passphrase", passphrase);
        args.put("timestamp", timestamp);
        args.put("sign", signature);

        Map<String, Object> request = new HashMap<>();
        request.put("op", "login");
        request.put("args", args);

        String message = JSON.toJSONString(request);
        webSocket.send(message);
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "Normal closure");
        }
    }

    /**
     * 连接成功回调
     */
    protected abstract void onConnected();

    /**
     * 连接断开回调
     */
    protected abstract void onDisconnected();

    /**
     * 发生错误回调
     *
     * @param throwable 异常信息
     */
    protected abstract void onError(Throwable throwable);

    /**
     * 收到消息回调
     *
     * @param message 消息内容
     */
    protected abstract void onMessageReceived(String message);
} 