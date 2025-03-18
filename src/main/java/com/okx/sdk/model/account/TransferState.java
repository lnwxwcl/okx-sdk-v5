package com.okx.sdk.model.account;

import lombok.Data;

@Data
public class TransferState {
    /**
     * 划转ID
     */
    private String transId;
    
    /**
     * 客户自定义ID
     */
    private String clientId;
    
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 划转数量
     */
    private String amt;
    
    /**
     * 资金转出账户
     */
    private String from;
    
    /**
     * 资金转入账户
     */
    private String to;
    
    /**
     * 子账户名称
     */
    private String subAcct;
    
    /**
     * 划转状态
     * success：成功
     * pending：处理中
     * failed：失败
     */
    private String state;
    
    /**
     * 划转时间，Unix时间戳的毫秒数格式
     */
    private String ts;
}