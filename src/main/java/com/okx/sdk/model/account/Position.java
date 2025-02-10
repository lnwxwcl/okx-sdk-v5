package com.okx.sdk.model.account;

import lombok.Data;

/**
 * 持仓信息
 */
@Data
public class Position {
    /**
     * 产品ID
     */
    private String instId;
    
    /**
     * 持仓ID
     */
    private String posId;
    
    /**
     * 持仓方向
     * long：多单
     * short：空单
     * net：净持仓
     */
    private String posSide;
    
    /**
     * 持仓数量
     */
    private String pos;
    
    /**
     * 开仓平均价
     */
    private String avgPx;
    
    /**
     * 未实现收益
     */
    private String upl;
    
    /**
     * 未实现收益率
     */
    private String uplRatio;
    
    /**
     * 杠杆倍数
     */
    private String lever;
    
    /**
     * 预估强平价
     */
    private String liqPx;
    
    /**
     * 标记价格
     */
    private String markPx;
    
    /**
     * 初始保证金
     */
    private String imr;
    
    /**
     * 维持保证金
     */
    private String margin;
    
    /**
     * 保证金率
     */
    private String mgnRatio;
    
    /**
     * 已实现收益
     */
    private String pnl;
    
    /**
     * 持仓方式
     * cross：全仓
     * isolated：逐仓
     */
    private String mgnMode;
    
    /**
     * 创建时间
     */
    private String cTime;
    
    /**
     * 更新时间
     */
    private String uTime;
    
    /**
     * 最新成交ID
     */
    private String tradeId;
    
    /**
     * 仓位状态
     * 1: 正常
     * 2: 等待强平
     * 3: 等待强平
     */
    private String state;
} 