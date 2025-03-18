package com.okx.sdk.example;

import com.alibaba.fastjson2.JSON;
import com.okx.sdk.websocket.OkxPublicWebSocketClient;
import com.okx.sdk.websocket.OkxPrivateWebSocketClient;
import com.okx.sdk.websocket.OkxWebSocketListener;

/**
 * OKX WebSocket API 使用示例
 */
public class WebSocketExample {
    public static void main(String[] args) {
        // 替换为您的API密钥信息
        String apiKey = "";
        String secretKey = "";
        String passphrase = "";

        // 创建公共频道WebSocket客户端
        OkxWebSocketListener publicListener = new OkxWebSocketListener() {
            @Override
            public void onMessage(String channel, Object data) {
                System.out.println("Public Channel Message - " + channel + ": " + JSON.toJSONString(data));
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Public Channel Error: " + throwable.getMessage());
            }

            @Override
            public void onClosed() {
                System.out.println("Public Channel Closed");
            }
        };

        OkxPublicWebSocketClient publicClient = new OkxPublicWebSocketClient(publicListener);
        publicClient.connect();

        // 创建私有频道WebSocket客户端
        OkxWebSocketListener privateListener = new OkxWebSocketListener() {
            @Override
            public void onMessage(String channel, Object data) {
                System.out.println("Private Channel Message - " + channel + ": " + JSON.toJSONString(data));
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Private Channel Error: " + throwable.getMessage());
            }

            @Override
            public void onClosed() {
                System.out.println("Private Channel Closed");
            }
        };

        OkxPrivateWebSocketClient privateClient = new OkxPrivateWebSocketClient(apiKey, secretKey, passphrase, privateListener);
        privateClient.connect();

        try {
            // 订阅公共频道数据
            Thread.sleep(1000); // 等待连接建立
            
            // 订阅BTC-USDT的Ticker数据
            publicClient.subscribeTicker("BTC-USDT");
            
            // 订阅BTC-USDT的1分钟K线数据
            publicClient.subscribeCandle("BTC-USDT", "1m");
            
            // 订阅BTC-USDT的深度数据
            publicClient.subscribeOrderBook("BTC-USDT", "5");
            
            // 订阅BTC-USDT的交易数据
            publicClient.subscribeTrades("BTC-USDT");

            // 订阅私有频道数据
            Thread.sleep(1000); // 等待登录完成
            
            // 订阅账户更新
            privateClient.subscribeAccount();
            
            // 订阅BTC-USDT的持仓更新
            privateClient.subscribePositions("SWAP", "BTC-USDT-SWAP");
            
            // 订阅BTC-USDT的订单更新
            privateClient.subscribeOrders("SWAP", "BTC-USDT-SWAP");

            // 运行一段时间后关闭连接
            Thread.sleep(30000);
            
            // 取消订阅
            publicClient.unsubscribeTicker("BTC-USDT");
            publicClient.unsubscribeCandle("BTC-USDT", "1m");
            publicClient.unsubscribeOrderBook("BTC-USDT", "5");
            publicClient.unsubscribeTrades("BTC-USDT");
            
            privateClient.unsubscribeAccount();
            privateClient.unsubscribePositions("SWAP", "BTC-USDT-SWAP");
            privateClient.unsubscribeOrders("SWAP", "BTC-USDT-SWAP");

            // 关闭连接
            publicClient.close();
            privateClient.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
} 