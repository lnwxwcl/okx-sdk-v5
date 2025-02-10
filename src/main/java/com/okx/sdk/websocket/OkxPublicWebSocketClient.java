package com.okx.sdk.websocket;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * OKX 公共频道WebSocket客户端
 */
@Slf4j
public class OkxPublicWebSocketClient extends OkxWebSocketClient {
    private final OkxWebSocketListener listener;

    public OkxPublicWebSocketClient(OkxWebSocketListener listener) {
        super(OkxWebSocketConfig.PUBLIC_WEBSOCKET_URL, null, null, null);
        this.listener = listener;
    }

    @Override
    protected void onConnected() {
        log.info("Public WebSocket connected");
    }

    @Override
    protected void onDisconnected() {
        log.info("Public WebSocket disconnected");
        listener.onClosed();
    }

    @Override
    protected void onError(Throwable throwable) {
        log.error("Public WebSocket error", throwable);
        listener.onError(throwable);
    }

    @Override
    protected void onMessageReceived(String message) {
        try {
            JSONObject json = JSON.parseObject(message);
            String event = json.getString("event");
            
            if ("error".equals(event)) {
                log.error("WebSocket error: {}", message);
                return;
            }
            
            if ("subscribe".equals(event)) {
                log.info("Channel subscribed: {}", message);
                return;
            }
            
            String channel = json.getString("arg");
            Object data = json.get("data");
            
            if (channel != null && data != null) {
                listener.onMessage(channel, data);
            }
        } catch (Exception e) {
            log.error("Failed to parse message: {}", message, e);
        }
    }

    /**
     * 订阅Ticker数据
     *
     * @param instId 产品ID
     */
    public void subscribeTicker(String instId) {
        Map<String, String> args = new HashMap<>();
        args.put("instId", instId);
        subscribe(OkxWebSocketConfig.TICKERS_CHANNEL, args);
    }

    /**
     * 订阅K线数据
     *
     * @param instId 产品ID
     * @param bar    时间粒度
     */
    public void subscribeCandle(String instId, String bar) {
        Map<String, String> args = new HashMap<>();
        args.put("instId", instId);
        args.put("bar", bar);
        subscribe(OkxWebSocketConfig.CANDLE_CHANNEL, args);
    }

    /**
     * 订阅深度数据
     *
     * @param instId 产品ID
     * @param size   深度档位
     */
    public void subscribeOrderBook(String instId, String size) {
        Map<String, String> args = new HashMap<>();
        args.put("instId", instId);
        args.put("sz", size);
        subscribe(OkxWebSocketConfig.BOOKS_CHANNEL, args);
    }

    /**
     * 订阅交易数据
     *
     * @param instId 产品ID
     */
    public void subscribeTrades(String instId) {
        Map<String, String> args = new HashMap<>();
        args.put("instId", instId);
        subscribe(OkxWebSocketConfig.TRADES_CHANNEL, args);
    }

    /**
     * 取消订阅Ticker数据
     *
     * @param instId 产品ID
     */
    public void unsubscribeTicker(String instId) {
        Map<String, String> args = new HashMap<>();
        args.put("instId", instId);
        unsubscribe(OkxWebSocketConfig.TICKERS_CHANNEL, args);
    }

    /**
     * 取消订阅K线数据
     *
     * @param instId 产品ID
     * @param bar    时间粒度
     */
    public void unsubscribeCandle(String instId, String bar) {
        Map<String, String> args = new HashMap<>();
        args.put("instId", instId);
        args.put("bar", bar);
        unsubscribe(OkxWebSocketConfig.CANDLE_CHANNEL, args);
    }

    /**
     * 取消订阅深度数据
     *
     * @param instId 产品ID
     * @param size   深度档位
     */
    public void unsubscribeOrderBook(String instId, String size) {
        Map<String, String> args = new HashMap<>();
        args.put("instId", instId);
        args.put("sz", size);
        unsubscribe(OkxWebSocketConfig.BOOKS_CHANNEL, args);
    }

    /**
     * 取消订阅交易数据
     *
     * @param instId 产品ID
     */
    public void unsubscribeTrades(String instId) {
        Map<String, String> args = new HashMap<>();
        args.put("instId", instId);
        unsubscribe(OkxWebSocketConfig.TRADES_CHANNEL, args);
    }
} 