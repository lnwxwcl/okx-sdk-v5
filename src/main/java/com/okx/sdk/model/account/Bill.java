package com.okx.sdk.model.account;

import lombok.Data;

/**
 * 账单信息
 */
@Data
public class Bill {
    /**
     * 账单ID
     */
    private String billId;
    
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 账单类型
     * 1：划转 2：交易 3：交割 4：自动换币 5：强平 6：保证金划转 7：扣息 8：资金费 9：自动减仓 10：穿仓补偿 11：系统换币 12：策略划转
     */
    private String type;
    
    /**
     * 账单数量
     */
    private String bal;
    
    /**
     * 账单变动数量
     */
    private String balChg;
    
    /**
     * 创建时间
     */
    private String ts;
    
    /**
     * 账户余额
     */
    private String after;
    
    /**
     * 交易ID
     */
    private String tradeId;
    
    /**
     * 备注
     */
    private String notes;
    
    /**
     * 产品类型
     */
    private String instType;
    
    /**
     * 产品ID
     */
    private String instId;
    
    /**
     * 订单ID
     */
    private String ordId;
    
    /**
     * 持仓ID
     */
    private String posId;
    
    /**
     * 子账户
     */
    private String subAcct;
    
    /**
     * 来源
     */
    private String from;
    
    /**
     * 目标
     */
    private String to;
    
    /**
     * 子类型
     */
    private String subType;
    
    /**
     * 手续费
     */
    private String fee;
    
    /**
     * 返佣
     */
    private String rebate;
    
    /**
     * 收益
     */
    private String pnl;
    
    /**
     * 账单类别
     * 1：划转手续费
     * 2：交易手续费
     * 3：资金费用
     * 4：投资组合保证金划转
     */
    private String category;
} 