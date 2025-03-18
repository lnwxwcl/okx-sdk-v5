package com.okx.sdk.service.market;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.market.Ticker;
import com.okx.sdk.model.market.Candlestick;
import com.okx.sdk.model.market.OrderBook;
import com.okx.sdk.model.market.ExchangeRate;
import com.okx.sdk.model.market.Instrument;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * OKX 市场数据服务接口
 */
public interface MarketDataService {
    /**
     * 获取所有产品行情信息
     *
     * @param instType 产品类型
     * @param uly      标的指数（仅适用于交割/永续/期权）
     * @param instId   产品ID
     * @return Ticker列表
     */
    OkxResponse<List<Ticker>> getTickers(String instType, String uly, String instId) throws IOException;

    /**
     * 获取单个产品行情信息
     *
     * @param instId 产品ID
     * @return Ticker信息
     */
    OkxResponse<List<Ticker>> getTicker(String instId) throws IOException;

    /**
     * 获取指数K线数据
     *
     * @param instId 产品ID
     * @param after  请求此时间戳之前（更旧的数据）的分页内容
     * @param before 请求此时间戳之后（更新的数据）的分页内容
     * @param bar    时间粒度，默认值1m
     * @param limit  分页返回的结果集数量，最大为300，默认100
     * @return K线数据列表
     */
    OkxResponse<List<Candlestick>> getCandlesticks(String instId, String after, String before, String bar, Integer limit) throws IOException;

    /**
     * 获取产品深度
     *
     * @param instId 产品ID
     * @param sz     深度档位数量，最大值400，即买卖深度共800条
     * @return 深度数据
     */
    OkxResponse<OrderBook> getOrderBook(String instId, Integer sz) throws IOException;

    /**
     * 获取法币汇率
     *
     * @return 汇率信息
     */
    OkxResponse<ExchangeRate> getExchangeRate() throws IOException;

    /**
     * 获取交易产品基础信息
     *
     * @param instType 产品类型
     * @param uly 标的指数
     * @param instId 产品ID
     * @return 产品信息列表
     */
    OkxResponse<List<Instrument>> getInstruments(String instType, String uly, String instId) throws IOException;

    /**
     * 获取交割和行权记录
     *
     * @param instType 产品类型
     * @param uly 标的指数
     * @param after 请求此时间戳之前（更旧的数据）的分页内容
     * @param before 请求此时间戳之后（更新的数据）的分页内容
     * @param limit 分页返回的结果集数量，最大为100，默认100
     * @return 交割记录列表
     */
    OkxResponse<List<Map<String, String>>> getDeliveryExerciseHistory(String instType, String uly,
                                                                    String after, String before,
                                                                    Integer limit) throws IOException;

    /**
     * 获取持仓总量
     *
     * @param instType 产品类型
     * @param uly 标的指数
     * @param instId 产品ID
     * @return 持仓总量信息
     */
    OkxResponse<List<Map<String, String>>> getOpenInterest(String instType, String uly,
                                                        String instId) throws IOException;

    /**
     * 获取永续合约当前资金费率
     *
     * @param instId 产品ID
     * @return 资金费率信息
     */
    OkxResponse<Map<String, String>> getFundingRate(String instId) throws IOException;

    /**
     * 获取永续合约历史资金费率
     *
     * @param instId 产品ID
     * @param after 请求此时间戳之前（更旧的数据）的分页内容
     * @param before 请求此时间戳之后（更新的数据）的分页内容
     * @param limit 分页返回的结果集数量，最大为100，默认100
     * @return 历史资金费率列表
     */
    OkxResponse<List<Map<String, String>>> getFundingRateHistory(String instId, String after,
                                                              String before, Integer limit) throws IOException;

    /**
     * 获取限价
     *
     * @param instType 产品类型
     * @param instId 产品ID
     * @param tdMode 交易模式
     * @return 限价信息
     */
    OkxResponse<Map<String, String>> getPriceLimit(String instType, String instId,
                                                String tdMode) throws IOException;

    /**
     * 获取期权定价
     *
     * @param instId 产品ID
     * @return 期权定价信息
     */
    OkxResponse<Map<String, String>> getOptionMarketData(String instId) throws IOException;

    /**
     * 获取预估交割/行权价格
     *
     * @param instId 产品ID
     * @return 预估价格信息
     */
    OkxResponse<Map<String, String>> getEstimatedPrice(String instId) throws IOException;

    /**
     * 获取指数行情
     *
     * @param quoteCcy 指数计价单位
     * @param instId 产品ID
     * @return 指数行情信息
     */
    OkxResponse<List<Map<String, String>>> getIndexTickers(String quoteCcy,
                                                        String instId) throws IOException;

    /**
     * 获取指数K线数据
     *
     * @param instId 产品ID
     * @param after 请求此时间戳之前（更旧的数据）的分页内容
     * @param before 请求此时间戳之后（更新的数据）的分页内容
     * @param bar K线周期
     * @param limit 分页返回的结果集数量，最大为100，默认100
     * @return K线数据列表
     */
    OkxResponse<List<Map<String, String>>> getIndexCandles(String instId, String after,
                                                        String before, String bar,
                                                        Integer limit) throws IOException;
} 