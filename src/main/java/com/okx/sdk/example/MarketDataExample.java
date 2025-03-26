package com.okx.sdk.example;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.market.Candlestick;
import com.okx.sdk.model.market.Instrument;
import com.okx.sdk.model.market.Ticker;
import com.okx.sdk.service.market.MarketDataService;
import com.okx.sdk.service.market.impl.MarketDataServiceImpl;
import com.okx.sdk.config.OkxConfig;

import java.io.IOException;
import java.util.*;

/**
 * OKX API 市场数据接口调用示例
 */
public class MarketDataExample {
    public static void main(String[] args) throws IOException {
        // 替换为您的API密钥信息
        String apiKey = "";
        String secretKey = "";
        String passphrase = "";
        // 创建市场数据服务实例
        MarketDataService marketDataService = new MarketDataServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);

        try {
            // 获取产品基础信息
            System.out.println("\n获取BTC-USDT产品信息...");
            OkxResponse<List<Instrument>> instrumentsResponse = marketDataService.getInstruments("SPOT", null, "TRB-USDT");
            if (instrumentsResponse.isSuccessful()) {
                List<Instrument> instruments = instrumentsResponse.getData();
                if (instruments != null && !instruments.isEmpty()) {
                    Instrument instrument = instruments.get(0);
                    System.out.println("产品信息:");
                    System.out.println("产品ID: " + instrument.getInstId());
                    System.out.println("交易货币: " + instrument.getBaseCcy());
                    System.out.println("计价货币: " + instrument.getQuoteCcy());
                    System.out.println("价格精度: " + instrument.getTickSz());
                    System.out.println("数量精度: " + instrument.getLotSz());
                    System.out.println("最小下单数量: " + instrument.getMinSz());
                    System.out.println("产品状态: " + instrument.getState());
                }
            } else {
                System.out.println("获取产品信息失败: " + instrumentsResponse.getMsg());
            }

            // 获取永续合约资金费率
            System.out.println("\n获取BTC-USDT-SWAP资金费率...");
            OkxResponse<Map<String, String>> fundingRateResponse = marketDataService.getFundingRate("BTC-USDT-SWAP");
            if (fundingRateResponse.isSuccessful()) {
                Map<String, String> fundingRate = fundingRateResponse.getData();
                if (fundingRate != null) {
                    System.out.println("当前资金费率: " + fundingRate.get("fundingRate"));
                    System.out.println("下一期资金费率: " + fundingRate.get("nextFundingRate"));
                    System.out.println("下一期结算时间: " + fundingRate.get("nextFundingTime"));
                }
            } else {
                System.out.println("获取资金费率失败: " + fundingRateResponse.getMsg());
            }

            // 获取持仓总量
            System.out.println("\n获取BTC-USDT-SWAP持仓总量...");
            OkxResponse<List<Map<String, String>>> openInterestResponse =
                    marketDataService.getOpenInterest("SWAP", null, "BTC-USDT-SWAP");
            if (openInterestResponse.isSuccessful()) {
                List<Map<String, String>> openInterest = openInterestResponse.getData();
                if (openInterest != null && !openInterest.isEmpty()) {
                    Map<String, String> data = openInterest.get(0);
                    System.out.println("持仓总量: " + data.get("oi") + " 张");
                    System.out.println("持仓总量USD价值: " + data.get("oiCcy") + " USD");
                }
            } else {
                System.out.println("获取持仓总量失败: " + openInterestResponse.getMsg());
            }

            // 获取指数行情
            System.out.println("\n获取BTC-USDT指数行情...");
            OkxResponse<List<Map<String, String>>> indexTickersResponse =
                    marketDataService.getIndexTickers(null, "BTC-USDT");
            if (indexTickersResponse.isSuccessful()) {
                List<Map<String, String>> tickers = indexTickersResponse.getData();
                if (tickers != null && !tickers.isEmpty()) {
                    Map<String, String> ticker = tickers.get(0);
                    System.out.println("指数最新价: " + ticker.get("idxPx"));
                    System.out.println("24小时最高价: " + ticker.get("high24h"));
                    System.out.println("24小时最低价: " + ticker.get("low24h"));
                    System.out.println("24小时涨跌幅: " + ticker.get("sodUtc0"));
                }
            } else {
                System.out.println("获取指数行情失败: " + indexTickersResponse.getMsg());
            }

        } catch (Exception e) {
            System.err.println("发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * MA(x)计算
     *
     * @param candlestick 烛台数据
     * @return 指标值doble
     */
    public static Double getMA(List<Candlestick> candlestick) {
        if (candlestick == null) {
            return Double.MAX_VALUE;
        }
        // 计算最近10个收盘价的平均值
        double sum = candlestick.stream()
                .mapToDouble(c -> Double.parseDouble(c.getC()))
                .sum();

        return sum / candlestick.size();
    }
}