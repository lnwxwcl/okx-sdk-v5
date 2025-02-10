package com.okx.sdk.model.market;

import lombok.Data;

/**
 * 产品行情信息
 */
@Data
public class Ticker {
    /**
     * 产品类型
     */
    private String instType;

    /**
     * 产品ID
     */
    private String instId;

    /**
     * 最新成交价
     */
    private String last;

    /**
     * 最新成交的数量
     */
    private String lastSz;

    /**
     * 卖一价
     */
    private String askPx;

    /**
     * 卖一价对应的数量
     */
    private String askSz;

    /**
     * 买一价
     */
    private String bidPx;

    /**
     * 买一价对应的数量
     */
    private String bidSz;

    /**
     * 24小时开盘价
     */
    private String open24h;

    /**
     * 24小时最高价
     */
    private String high24h;

    /**
     * 24小时最低价
     */
    private String low24h;

    /**
     * 24小时成交量，以币为单位
     */
    private String vol24h;

    /**
     * 24小时成交量，以计价货币为单位
     */
    private String volCcy24h;

    /**
     * UTC 0 时开盘价
     */
    private String sodUtc0;

    /**
     * UTC+8 时开盘价
     */
    private String sodUtc8;

    /**
     * 系统时间戳
     */
    private String ts;
} 