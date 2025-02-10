package com.okx.sdk.model.market;

import lombok.Data;
import java.util.List;

/**
 * 订单簿数据
 */
@Data
public class OrderBook {
    /**
     * 产品ID
     */
    private String instId;

    /**
     * 卖方深度 [价格, 数量, 流动性, 订单数]
     */
    private List<List<String>> asks;

    /**
     * 买方深度 [价格, 数量, 流动性, 订单数]
     */
    private List<List<String>> bids;

    /**
     * 系统时间戳
     */
    private String ts;
} 