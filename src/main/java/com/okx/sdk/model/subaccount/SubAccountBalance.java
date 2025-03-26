package com.okx.sdk.model.subaccount;

import lombok.Data;

/**
 * 子账户资金余额信息
 */
@Data
public class SubAccountBalance {
    /**
     * 子账户名称
     */
    private String subAcct;
    
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 余额
     */
    private String bal;
    
    /**
     * 可用余额
     */
    private String availBal;
    
    /**
     * 冻结余额
     */
    private String frozenBal;
    
    /**
     * 更新时间
     */
    private String uTime;
} 