package com.okx.sdk.model.funding;

import lombok.Data;

/**
 * 充值记录信息
 */
@Data
public class DepositHistory {
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 链信息
     */
    private String chain;
    
    /**
     * 充值数量
     */
    private String amt;
    
    /**
     * 交易ID
     */
    private String txId;
    
    /**
     * 充值地址
     */
    private String from;
    
    /**
     * 到账地址
     */
    private String to;
    
    /**
     * 充值状态
     * 0：等待确认
     * 1：确认到账
     * 2：充值成功
     * 8：因该币种暂停充值而未到账，恢复充值后自动到账
     * 11：命中地址黑名单
     * 12：账户或充值被冻结
     * 13：子账户充值拦截
     */
    private String state;
    
    /**
     * 充值时间
     */
    private String ts;
    
    /**
     * 区块确认数
     */
    private String confirmations;
    
    /**
     * 区块高度
     */
    private String blockHeight;
    
    /**
     * 区块哈希
     */
    private String blockHash;
} 