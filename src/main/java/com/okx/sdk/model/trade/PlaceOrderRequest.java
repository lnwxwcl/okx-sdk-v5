package com.okx.sdk.model.trade;

import lombok.Builder;
import lombok.Data;

/**
 * 下单请求参数
 */
@Data
@Builder
public class PlaceOrderRequest {
    /**
     * 产品ID
     */
    private String instId;

    /**
     * 交易模式
     * 保证金模式：isolated：逐仓 cross：全仓
     * 非保证金模式：cash：非保证金
     */
    private String tdMode;

    /**
     * 订单类型
     * market：市价单
     * limit：限价单
     * post_only：只做maker单
     * fok：全部成交或立即取消
     * ioc：立即成交并取消剩余
     * optimal_limit_ioc：市价委托立即成交并取消剩余（仅适用交割、永续）
     */
    private String ordType;

    /**
     * 委托数量 buy时设置需要花费USDT的数量如7USDT，sell时设置卖出币种的数量 如ETH0.012
     */
    private String sz;

    /**
     * 委托价格，仅适用于limit、post_only、fok、ioc类型的订单
     */
    private String px;

    /**
     * 订单方向
     * buy：买，sell：卖
     */
    private String side;

    /**
     * 持仓方向
     * 在双向持仓模式下必填，且仅可选择 long 或 short
     */
    private String posSide;

    /**
     * 市价单委托数量的类型
     * base_ccy: 交易货币 ；quote_ccy：计价货币
     */
    private String tgtCcy;

    /**
     * 是否只减仓，true 或 false，默认false
     */
    private Boolean reduceOnly;

    /**
     * 客户自定义订单ID
     * 字母（区分大小写）与数字的组合，可以是纯字母、纯数字且长度要在1-32位之间
     */
    private String clOrdId;

    /**
     * 订单标签
     */
    private String tag;
} 