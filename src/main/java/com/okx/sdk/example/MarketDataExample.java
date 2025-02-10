package com.okx.sdk.example;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.market.Ticker;
import com.okx.sdk.model.market.Candlestick;
import com.okx.sdk.model.market.OrderBook;
import com.okx.sdk.service.market.MarketDataService;
import com.okx.sdk.service.market.impl.MarketDataServiceImpl;
import com.okx.sdk.config.OkxConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OKX API 市场数据接口调用示例
 */
public class MarketDataExample {
    public static void main(String[] args) {
        // 替换为您的API密钥信息
        String apiKey = "YOUR-API-KEY";
        String secretKey = "YOUR-SECRET-KEY";
        String passphrase = "YOUR-PASSPHRASE";

        // 创建市场数据服务实例
        MarketDataService marketDataService = new MarketDataServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);

        try {
            // 获取BTC-USDT的行情信息
            System.out.println("获取BTC-USDT行情信息...");
            OkxResponse<List<Ticker>> tickerResponse = marketDataService.getTicker("BTC-USDT");
            if (tickerResponse.isSuccessful()) {
                Ticker ticker = tickerResponse.getData().get(0);
                System.out.println("BTC-USDT 最新价格: " + ticker.getLast());
                System.out.println("24小时最高价: " + ticker.getHigh24h());
                System.out.println("24小时最低价: " + ticker.getLow24h());
                System.out.println("24小时成交量: " + ticker.getVol24h());
            } else {
                System.out.println("获取行情信息失败: " + tickerResponse.getMsg());
            }

            // 获取BTC-USDT的K线数据
            System.out.println("\n获取BTC-USDT K线数据...");
            OkxResponse<List<Candlestick>> candlesResponse = marketDataService.getCandlesticks("BTC-USDT", null, null, "1m", 10);
            if (candlesResponse.isSuccessful()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("BTC-USDT 最近10根K线数据:");
                System.out.println("时间\t\t\t开盘价\t\t最高价\t\t最低价\t\t收盘价\t\t成交量");
                for (Candlestick candle : candlesResponse.getData()) {
                    String time = sdf.format(new Date(Long.parseLong(candle.getTs())));
                    System.out.printf("%s\t%s\t%s\t%s\t%s\t%s%n",
                            time, candle.getO(), candle.getH(), candle.getL(), candle.getC(), candle.getVol());
                }
            } else {
                System.out.println("获取K线数据失败: " + candlesResponse.getMsg());
            }

            // 获取BTC-USDT的深度数据
            System.out.println("\n获取BTC-USDT深度数据...");
            OkxResponse<OrderBook> depthResponse = marketDataService.getOrderBook("BTC-USDT", 5);
            if (depthResponse.isSuccessful()) {
                OrderBook orderBook = depthResponse.getData();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String updateTime = sdf.format(new Date(Long.parseLong(orderBook.getTs())));
                
                System.out.println("BTC-USDT 市场深度 (更新时间: " + updateTime + ")");
                System.out.println("\n卖单 (价格 / 数量 / 订单数):");
                System.out.println("------------------------------");
                for (List<String> ask : orderBook.getAsks()) {
                    System.out.printf("%s / %s / %s%n", 
                            ask.get(0),    // 价格
                            ask.get(1),    // 数量
                            ask.get(3));   // 订单数
                }
                
                System.out.println("\n买单 (价格 / 数量 / 订单数):");
                System.out.println("------------------------------");
                for (List<String> bid : orderBook.getBids()) {
                    System.out.printf("%s / %s / %s%n", 
                            bid.get(0),    // 价格
                            bid.get(1),    // 数量
                            bid.get(3));   // 订单数
                }
            } else {
                System.out.println("获取深度数据失败: " + depthResponse.getMsg());
            }

        } catch (Exception e) {
            System.err.println("发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 