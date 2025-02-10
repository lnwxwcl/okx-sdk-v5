package com.okx.sdk.websocket;

/**
 * WebSocket配置常量
 */
public class OkxWebSocketConfig {
    /**
     * 公共频道WebSocket地址
     */
    public static final String PUBLIC_WEBSOCKET_URL = "wss://ws.okx.com:8443/ws/v5/public";

    /**
     * 私有频道WebSocket地址
     */
    public static final String PRIVATE_WEBSOCKET_URL = "wss://ws.okx.com:8443/ws/v5/private";

    /**
     * 业务频道WebSocket地址
     */
    public static final String BUSINESS_WEBSOCKET_URL = "wss://ws.okx.com:8443/ws/v5/business";

    /**
     * 行情Tickers频道
     */
    public static final String TICKERS_CHANNEL = "tickers";

    /**
     * 行情K线频道
     */
    public static final String CANDLE_CHANNEL = "candle";

    /**
     * 行情深度频道
     */
    public static final String BOOKS_CHANNEL = "books";

    /**
     * 行情交易频道
     */
    public static final String TRADES_CHANNEL = "trades";

    /**
     * 账户频道
     */
    public static final String ACCOUNT_CHANNEL = "account";

    /**
     * 持仓频道
     */
    public static final String POSITIONS_CHANNEL = "positions";

    /**
     * 订单频道
     */
    public static final String ORDERS_CHANNEL = "orders";

    /**
     * 策略委托订单频道
     */
    public static final String ALGO_ORDERS_CHANNEL = "algo-orders";
} 