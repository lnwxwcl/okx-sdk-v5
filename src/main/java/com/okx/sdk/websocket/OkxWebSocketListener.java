package com.okx.sdk.websocket;

/**
 * WebSocket消息监听器
 */
public interface OkxWebSocketListener {
    /**
     * 收到消息回调
     *
     * @param channel 频道名称
     * @param data    消息数据
     */
    void onMessage(String channel, Object data);

    /**
     * 发生错误回调
     *
     * @param throwable 异常信息
     */
    void onError(Throwable throwable);

    /**
     * 连接断开回调
     */
    void onClosed();
} 