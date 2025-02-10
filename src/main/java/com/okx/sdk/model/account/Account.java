package com.okx.sdk.model.account;

import lombok.Data;
import java.util.List;

/**
 * 账户信息
 */
@Data
public class Account {
    /**
     * 账户ID
     */
    private String uid;
    
    /**
     * 账户总权益 = 美金层面权益
     */
    private String totalEq;
    
    /**
     * 币种层面权益
     */
    private String isoEq;
    
    /**
     * 美金层面权益
     */
    private String adjEq;
    
    /**
     * 订单冻结数量
     */
    private String ordFroz;
    
    /**
     * 币种余额信息
     */
    private List<Balance> details;
    
    /**
     * 更新时间
     */
    private String uTime;
    
    /**
     * 账户层级
     */
    private String acctLv;
    
    /**
     * 自动借币
     */
    private String autoLoan;
    
    /**
     * 币种余额信息
     */
    @Data
    public static class Balance {
        /**
         * 币种
         */
        private String ccy;
        
        /**
         * 币种总权益
         */
        private String eq;
        
        /**
         * 币种余额
         */
        private String cashBal;
        
        /**
         * 币种冻结数量
         */
        private String frozenBal;
        
        /**
         * 可用余额
         */
        private String availBal;
        
        /**
         * 币种估值
         */
        private String eqUsd;
        
        /**
         * 币种折算率
         */
        private String disEq;
        
        /**
         * 是否可充提
         */
        private Boolean availableDeposit;
        
        /**
         * 是否可交易
         */
        private Boolean availableTrade;
    }
} 