package com.okx.sdk.model.market;

import lombok.Data;

/**
 * 产品信息
 */
@Data
public class Instrument {
    /**
     * 产品ID
     */
    private String instId;
    
    /**
     * 产品类型
     * SPOT：币币
     * MARGIN：币币杠杆
     * SWAP：永续合约
     * FUTURES：交割合约
     * OPTION：期权
     */
    private String instType;
    
    /**
     * 标的指数，如 BTC-USD
     */
    private String uly;
    
    /**
     * 交易货币币种，如 BTC-USDT 中的 BTC
     */
    private String baseCcy;
    
    /**
     * 计价货币币种，如 BTC-USDT 中的 USDT
     */
    private String quoteCcy;
    
    /**
     * 交割/行权货币币种
     * 仅适用于交割/期权
     */
    private String settleCcy;
    
    /**
     * 合约乘数
     * 仅适用于交割/永续/期权
     */
    private String ctMult;
    
    /**
     * 合约面值
     */
    private String ctVal;
    
    /**
     * 合约面值计价币种
     * 仅适用于交割/永续/期权
     */
    private String ctValCcy;
    
    /**
     * 期权类型，C：看涨期权 P：看跌期权
     * 仅适用于期权
     */
    private String optType;
    
    /**
     * 行权价格
     * 仅适用于期权
     */
    private String stk;
    
    /**
     * 行权价格间距
     * 仅适用于期权
     */
    private String stkPxVol;
    
    /**
     * 到期日期
     * 仅适用于交割/期权
     */
    private String expTime;
    
    /**
     * 下单价格精度，如 0.0001
     */
    private String tickSz;
    
    /**
     * 下单数量精度
     */
    private String lotSz;
    
    /**
     * 最小下单数量
     */
    private String minSz;
    
    /**
     * 合约或现货限价单的单笔最大委托数量
     */
    private String maxLmtSz;
    
    /**
     * 合约或现货市价单的单笔最大委托数量
     */
    private String maxMktSz;
    
    /**
     * 合约或现货时的单笔最大市价开仓数量
     */
    private String maxTwapSz;
    
    /**
     * 合约或现货时的单笔最大计划委托数量
     */
    private String maxIcebergSz;
    
    /**
     * 合约或现货时的单笔最大触发委托数量
     */
    private String maxTriggerSz;
    
    /**
     * 合约或现货时的单笔最大止盈止损数量
     */
    private String maxStopSz;
    
    /**
     * 杠杆倍数
     */
    private String lever;
    
    /**
     * 维持保证金率
     * 仅适用于币币杠杆/交割/永续
     */
    private String maintMarginRatio;
    
    /**
     * 最大可开杠杆倍数
     * 仅适用于币币杠杆/交割/永续
     */
    private String maxLever;
    
    /**
     * 标记价格
     * 仅适用于永续/交割/期权
     */
    private String markPrice;
    
    /**
     * 产品状态
     * live：交易中
     * suspend：暂停中
     * preopen：预上线
     * test：测试中
     * expired：已过期（仅适用于交割/期权）
     */
    private String state;
    
    /**
     * 上线时间
     */
    private String listTime;
    
    /**
     * 产品类别
     * 0：普通
     * 1：创新
     * 2：临时
     */
    private String category;
    
    /**
     * 期权价格是否处于运行中
     * 仅适用于期权
     */
    private String optPriceRun;
    
    /**
     * 期权价格运行状态
     * 仅适用于期权
     */
    private String optPriceState;
    
    /**
     * 期权价格
     * 仅适用于期权
     */
    private String optPrice;
    
    /**
     * 期权价格更新时间
     * 仅适用于期权
     */
    private String optPriceUpdate;
    
    /**
     * 期权波动率
     * 仅适用于期权
     */
    private String volatility;
    
    /**
     * 期权希腊字母Delta
     * 仅适用于期权
     */
    private String delta;
    
    /**
     * 期权希腊字母Gamma
     * 仅适用于期权
     */
    private String gamma;
    
    /**
     * 期权希腊字母Theta
     * 仅适用于期权
     */
    private String theta;
    
    /**
     * 期权希腊字母Vega
     * 仅适用于期权
     */
    private String vega;
    
    /**
     * 期权希腊字母Rho
     * 仅适用于期权
     */
    private String rho;
    
    /**
     * 期权隐含波动率
     * 仅适用于期权
     */
    private String iv;
    
    /**
     * 期权最新成交价
     * 仅适用于期权
     */
    private String last;
    
    /**
     * 期权持仓量
     * 仅适用于期权
     */
    private String openInterest;
    
    /**
     * 期权24小时成交量
     * 仅适用于期权
     */
    private String vol24h;
    
    /**
     * 期权24小时成交额
     * 仅适用于期权
     */
    private String volCcy24h;
    
    /**
     * 期权标的指数价格
     * 仅适用于期权
     */
    private String ulyPrice;
} 