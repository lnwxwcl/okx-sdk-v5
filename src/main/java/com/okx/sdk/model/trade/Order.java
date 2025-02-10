package com.okx.sdk.model.trade;

import lombok.Data;

/**
 * 订单信息
 */
@Data
public class Order {
    /**
     * 产品ID
     */
    private String instId;

    /**
     * 订单ID
     */
    private String ordId;

    /**
     * 客户自定义订单ID
     */
    private String clOrdId;

    /**
     * 订单标签
     */
    private String tag;

    /**
     * 委托价格
     */
    private String px;

    /**
     * 委托数量
     */
    private String sz;

    /**
     * 订单类型
     */
    private String ordType;

    /**
     * 订单方向
     */
    private String side;

    /**
     * 持仓方向
     */
    private String posSide;

    /**
     * 交易模式
     */
    private String tdMode;

    /**
     * 累计成交数量
     */
    private String accFillSz;

    /**
     * 最新成交价格
     */
    private String fillPx;

    /**
     * 最新成交ID
     */
    private String tradeId;

    /**
     * 最新成交数量
     */
    private String fillSz;

    /**
     * 最新成交时间
     */
    private String fillTime;

    /**
     * 订单状态
     * canceled：撤单成功
     * live：等待成交
     * partially_filled：部分成交
     * filled：完全成交
     */
    private String state;

    /**
     * 订单创建时间
     */
    private String cTime;

    /**
     * 订单更新时间
     */
    private String uTime;

    /**
     * 成交均价
     */
    private String avgPx;

    /**
     * 手续费币种
     */
    private String feeCcy;

    /**
     * 手续费
     */
    private String fee;

    /**
     * 返佣金币种
     */
    private String rebateCcy;

    /**
     * 返佣金额
     */
    private String rebate;

    /**
     * 收益
     */
    private String pnl;

    /**
     * 订单来源
     * 13:策略委托单触发后的生成的限价单
     */
    private String source;

    /**
     * 订单类别
     * normal：普通委托
     * twap：TWAP自动换币
     * adl：ADL自动减仓
     * full_liquidation：强制平仓
     * partial_liquidation：强制减仓
     * delivery：交割
     * ddh：对冲减仓类型订单
     */
    private String category;
} 