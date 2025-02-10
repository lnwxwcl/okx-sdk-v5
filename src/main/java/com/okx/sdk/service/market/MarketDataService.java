package com.okx.sdk.service.market;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.market.Ticker;
import com.okx.sdk.model.market.Candlestick;
import com.okx.sdk.model.market.OrderBook;

import java.io.IOException;
import java.util.List;

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
} 