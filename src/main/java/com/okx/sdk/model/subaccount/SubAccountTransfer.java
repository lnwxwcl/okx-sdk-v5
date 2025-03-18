package com.okx.sdk.model.subaccount;

import lombok.Data;
import lombok.Builder;

/**
 * 子账户转账请求
 */
@Data
@Builder
public class SubAccountTransfer {
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 转账数量
     */
    private String amt;
    
    /**
     * 转出子账户类型
     * 6：资金账户
     * 18：交易账户
     */
    private String from;
    
    /**
     * 转入子账户类型
     * 6：资金账户
     * 18：交易账户
     */
    private String to;
    
    /**
     * 转出账户的账户名称
     */
    private String fromSubAccount;
    
    /**
     * 转入账户的账户名称
     */
    private String toSubAccount;
} 