package com.okx.sdk.websocket;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * OKX 私有频道WebSocket客户端
 */
@Slf4j
public class OkxPrivateWebSocketClient extends OkxWebSocketClient {
    private final OkxWebSocketListener listener;
    private boolean isLoggedIn = false;

    public OkxPrivateWebSocketClient(String apiKey, String secretKey, String passphrase, OkxWebSocketListener listener) {
        super(OkxWebSocketConfig.PRIVATE_WEBSOCKET_URL, apiKey, secretKey, passphrase);
        this.listener = listener;
    }

    @Override
    protected void onConnected() {
        log.info("Private WebSocket connected");
        login();
    }

    @Override
    protected void onDisconnected() {
        log.info("Private WebSocket disconnected");
        isLoggedIn = false;
        listener.onClosed();
    }

    @Override
    protected void onError(Throwable throwable) {
        log.error("Private WebSocket error", throwable);
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
            
            if ("login".equals(event)) {
                isLoggedIn = true;
                log.info("WebSocket logged in");
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
     * 订阅账户更新
     */
    public void subscribeAccount() {
        if (!isLoggedIn) {
            log.warn("WebSocket not logged in");
            return;
        }
        subscribe(OkxWebSocketConfig.ACCOUNT_CHANNEL, new HashMap<>());
    }

    /**
     * 订阅持仓更新
     *
     * @param instType 产品类型
     * @param instId   产品ID
     */
    public void subscribePositions(String instType, String instId) {
        if (!isLoggedIn) {
            log.warn("WebSocket not logged in");
            return;
        }
        Map<String, String> args = new HashMap<>();
        if (instType != null) {
            args.put("instType", instType);
        }
        if (instId != null) {
            args.put("instId", instId);
        }
        subscribe(OkxWebSocketConfig.POSITIONS_CHANNEL, args);
    }

    /**
     * 订阅订单更新
     *
     * @param instType 产品类型
     * @param instId   产品ID
     */
    public void subscribeOrders(String instType, String instId) {
        if (!isLoggedIn) {
            log.warn("WebSocket not logged in");
            return;
        }
        Map<String, String> args = new HashMap<>();
        if (instType != null) {
            args.put("instType", instType);
        }
        if (instId != null) {
            args.put("instId", instId);
        }
        subscribe(OkxWebSocketConfig.ORDERS_CHANNEL, args);
    }

    /**
     * 订阅策略委托订单更新
     *
     * @param instType 产品类型
     * @param instId   产品ID
     */
    public void subscribeAlgoOrders(String instType, String instId) {
        if (!isLoggedIn) {
            log.warn("WebSocket not logged in");
            return;
        }
        Map<String, String> args = new HashMap<>();
        if (instType != null) {
            args.put("instType", instType);
        }
        if (instId != null) {
            args.put("instId", instId);
        }
        subscribe(OkxWebSocketConfig.ALGO_ORDERS_CHANNEL, args);
    }

    /**
     * 取消订阅账户更新
     */
    public void unsubscribeAccount() {
        if (!isLoggedIn) {
            log.warn("WebSocket not logged in");
            return;
        }
        unsubscribe(OkxWebSocketConfig.ACCOUNT_CHANNEL, new HashMap<>());
    }

    /**
     * 取消订阅持仓更新
     *
     * @param instType 产品类型
     * @param instId   产品ID
     */
    public void unsubscribePositions(String instType, String instId) {
        if (!isLoggedIn) {
            log.warn("WebSocket not logged in");
            return;
        }
        Map<String, String> args = new HashMap<>();
        if (instType != null) {
            args.put("instType", instType);
        }
        if (instId != null) {
            args.put("instId", instId);
        }
        unsubscribe(OkxWebSocketConfig.POSITIONS_CHANNEL, args);
    }

    /**
     * 取消订阅订单更新
     *
     * @param instType 产品类型
     * @param instId   产品ID
     */
    public void unsubscribeOrders(String instType, String instId) {
        if (!isLoggedIn) {
            log.warn("WebSocket not logged in");
            return;
        }
        Map<String, String> args = new HashMap<>();
        if (instType != null) {
            args.put("instType", instType);
        }
        if (instId != null) {
            args.put("instId", instId);
        }
        unsubscribe(OkxWebSocketConfig.ORDERS_CHANNEL, args);
    }

    /**
     * 取消订阅策略委托订单更新
     *
     * @param instType 产品类型
     * @param instId   产品ID
     */
    public void unsubscribeAlgoOrders(String instType, String instId) {
        if (!isLoggedIn) {
            log.warn("WebSocket not logged in");
            return;
        }
        Map<String, String> args = new HashMap<>();
        if (instType != null) {
            args.put("instType", instType);
        }
        if (instId != null) {
            args.put("instId", instId);
        }
        unsubscribe(OkxWebSocketConfig.ALGO_ORDERS_CHANNEL, args);
    }
} 