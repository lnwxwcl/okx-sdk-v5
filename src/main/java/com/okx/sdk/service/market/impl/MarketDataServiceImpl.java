package com.okx.sdk.service.market.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.okx.sdk.client.OkxRestClient;
import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.market.Ticker;
import com.okx.sdk.model.market.Candlestick;
import com.okx.sdk.model.market.OrderBook;
import com.okx.sdk.model.market.ExchangeRate;
import com.okx.sdk.model.market.Instrument;
import com.okx.sdk.service.market.MarketDataService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 市场数据服务实现类
 */
public class MarketDataServiceImpl extends OkxRestClient implements MarketDataService {

    public MarketDataServiceImpl(String apiKey, String secretKey, String passphrase, String baseUrl) {
        super(apiKey, secretKey, passphrase, baseUrl);
    }

    @Override
    public OkxResponse<List<Ticker>> getTickers(String instType, String uly, String instId) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(uly)) {
            params.add("uly=" + uly);
        }
        if (StringUtils.isNotEmpty(instId)) {
            params.add("instId=" + instId);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/market/tickers", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Ticker>>>() {});
    }

    @Override
    public OkxResponse<List<Ticker>> getTicker(String instId) throws IOException {
        String response = get("/api/v5/market/ticker", "instId=" + instId);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Ticker>>>() {});
    }

    @Override
    public OkxResponse<List<Candlestick>> getCandlesticks(String instId, String after, String before, String bar, Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("instId=" + instId);

        if (StringUtils.isNotEmpty(after)) {
            params.add("after=" + after);
        }
        if (StringUtils.isNotEmpty(before)) {
            params.add("before=" + before);
        }
        if (StringUtils.isNotEmpty(bar)) {
            params.add("bar=" + bar);
        }
        if (limit != null) {
            params.add("limit=" + limit);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/market/candles", queryString);
        JSONObject jsonObject = JSON.parseObject(response);

        OkxResponse<List<Candlestick>> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));

        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null) {
            List<Candlestick> candlesticks = new ArrayList<>();
            for (int i = 0; i < dataArray.size(); i++) {
                JSONArray candleArray = dataArray.getJSONArray(i);
                String[] candleData = new String[candleArray.size()];
                for (int j = 0; j < candleArray.size(); j++) {
                    candleData[j] = candleArray.getString(j);
                }
                candlesticks.add(Candlestick.fromArray(candleData));
            }
            result.setData(candlesticks);
        }

        return result;
    }

    @Override
    public OkxResponse<OrderBook> getOrderBook(String instId, Integer sz) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("instId=" + instId);

        if (sz != null) {
            params.add("sz=" + sz);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/market/books", queryString);
        JSONObject jsonObject = JSON.parseObject(response);

        OkxResponse<OrderBook> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));

        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null && !dataArray.isEmpty()) {
            JSONObject orderBookData = dataArray.getJSONObject(0);
            OrderBook orderBook = new OrderBook();

            // 处理卖单数据
            JSONArray asksArray = orderBookData.getJSONArray("asks");
            List<List<String>> asks = new ArrayList<>();
            for (int i = 0; i < asksArray.size(); i++) {
                JSONArray askItem = asksArray.getJSONArray(i);
                List<String> ask = new ArrayList<>();
                for (int j = 0; j < askItem.size(); j++) {
                    ask.add(askItem.getString(j));
                }
                asks.add(ask);
            }
            orderBook.setAsks(asks);

            // 处理买单数据
            JSONArray bidsArray = orderBookData.getJSONArray("bids");
            List<List<String>> bids = new ArrayList<>();
            for (int i = 0; i < bidsArray.size(); i++) {
                JSONArray bidItem = bidsArray.getJSONArray(i);
                List<String> bid = new ArrayList<>();
                for (int j = 0; j < bidItem.size(); j++) {
                    bid.add(bidItem.getString(j));
                }
                bids.add(bid);
            }
            orderBook.setBids(bids);

            orderBook.setTs(orderBookData.getString("ts"));
            result.setData(orderBook);
        }

        return result;
    }

    @Override
    public OkxResponse<ExchangeRate> getExchangeRate() throws IOException {
        String response = get("/api/v5/market/exchange-rate", null);
        JSONObject jsonObject = JSON.parseObject(response);

        OkxResponse<ExchangeRate> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));

        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null && !dataArray.isEmpty()) {
            result.setData(dataArray.getObject(0, ExchangeRate.class));
        }

        return result;
    }

    @Override
    public OkxResponse<List<Instrument>> getInstruments(String instType, String uly, String instId) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(uly)) {
            params.add("uly=" + uly);
        }
        if (StringUtils.isNotEmpty(instId)) {
            params.add("instId=" + instId);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/public/instruments", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Instrument>>>() {});
    }

    @Override
    public OkxResponse<List<Map<String, String>>> getDeliveryExerciseHistory(String instType, String uly,
                                                                             String after, String before,
                                                                             Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(uly)) {
            params.add("uly=" + uly);
        }
        if (StringUtils.isNotEmpty(after)) {
            params.add("after=" + after);
        }
        if (StringUtils.isNotEmpty(before)) {
            params.add("before=" + before);
        }
        if (limit != null) {
            params.add("limit=" + limit);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/public/delivery-exercise-history", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Map<String, String>>>>() {});
    }

    @Override
    public OkxResponse<List<Map<String, String>>> getOpenInterest(String instType, String uly,
                                                                  String instId) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(uly)) {
            params.add("uly=" + uly);
        }
        if (StringUtils.isNotEmpty(instId)) {
            params.add("instId=" + instId);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/public/open-interest", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Map<String, String>>>>() {});
    }

    @Override
    public OkxResponse<Map<String, String>> getFundingRate(String instId) throws IOException {
        String queryString = "instId=" + instId;
        String response = get("/api/v5/public/funding-rate", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Map<String, String>>>() {});
    }

    @Override
    public OkxResponse<List<Map<String, String>>> getFundingRateHistory(String instId, String after,
                                                                        String before, Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("instId=" + instId);
        if (StringUtils.isNotEmpty(after)) {
            params.add("after=" + after);
        }
        if (StringUtils.isNotEmpty(before)) {
            params.add("before=" + before);
        }
        if (limit != null) {
            params.add("limit=" + limit);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/public/funding-rate-history", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Map<String, String>>>>() {});
    }

    @Override
    public OkxResponse<Map<String, String>> getPriceLimit(String instType, String instId,
                                                          String tdMode) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(instId)) {
            params.add("instId=" + instId);
        }
        if (StringUtils.isNotEmpty(tdMode)) {
            params.add("tdMode=" + tdMode);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/public/price-limit", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Map<String, String>>>() {});
    }

    @Override
    public OkxResponse<Map<String, String>> getOptionMarketData(String instId) throws IOException {
        String queryString = "instId=" + instId;
        String response = get("/api/v5/public/opt-summary", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Map<String, String>>>() {});
    }

    @Override
    public OkxResponse<Map<String, String>> getEstimatedPrice(String instId) throws IOException {
        String queryString = "instId=" + instId;
        String response = get("/api/v5/public/estimated-price", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Map<String, String>>>() {});
    }

    @Override
    public OkxResponse<List<Map<String, String>>> getIndexTickers(String quoteCcy,
                                                                  String instId) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(quoteCcy)) {
            params.add("quoteCcy=" + quoteCcy);
        }
        if (StringUtils.isNotEmpty(instId)) {
            params.add("instId=" + instId);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/market/index-tickers", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Map<String, String>>>>() {});
    }

    @Override
    public OkxResponse<List<Map<String, String>>> getIndexCandles(String instId, String after,
                                                                  String before, String bar,
                                                                  Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("instId=" + instId);
        if (StringUtils.isNotEmpty(after)) {
            params.add("after=" + after);
        }
        if (StringUtils.isNotEmpty(before)) {
            params.add("before=" + before);
        }
        if (StringUtils.isNotEmpty(bar)) {
            params.add("bar=" + bar);
        }
        if (limit != null) {
            params.add("limit=" + limit);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/market/index-candles", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Map<String, String>>>>() {});
    }
} 