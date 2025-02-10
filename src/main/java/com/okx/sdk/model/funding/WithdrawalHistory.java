package com.okx.sdk.model.funding;

import lombok.Data;

/**
 * 提现记录信息
 */
@Data
public class WithdrawalHistory {
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 链信息
     */
    private String chain;
    
    /**
     * 提现数量
     */
    private String amt;
    
    /**
     * 提现ID
     */
    private String wdId;
    
    /**
     * 交易ID
     */
    private String txId;
    
    /**
     * 提现地址
     */
    private String from;
    
    /**
     * 收币地址
     */
    private String to;
    
    /**
     * 提现状态
     * -3：撤销中
     * -2：已撤销
     * -1：失败
     * 0：等待提现
     * 1：提现中
     * 2：提现成功
     * 7：审核通过
     * 10：等待确认
     * 12：发送中
     */
    private String state;
    
    /**
     * 提现时间
     */
    private String ts;
    
    /**
     * 提现手续费
     */
    private String fee;
    
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
    
    /**
     * 客户自定义提现ID
     */
    private String clientId;
} 