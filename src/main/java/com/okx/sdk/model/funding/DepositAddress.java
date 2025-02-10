package com.okx.sdk.model.funding;

import lombok.Data;

/**
 * 充值地址信息
 */
@Data
public class DepositAddress {
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 链信息
     */
    private String chain;
    
    /**
     * 充值地址
     */
    private String addr;
    
    /**
     * 充值地址标签，如有
     */
    private String tag;
    
    /**
     * 充值地址备注，如有
     */
    private String memo;
    
    /**
     * 充值地址支持的付款方式
     * 1: 支持链上转账
     * 2: 支持地址簿转账
     */
    private String pmtMethods;
    
    /**
     * 充值地址创建时间
     */
    private String cTime;
    
    /**
     * 该地址是否为托管地址
     */
    private Boolean selected;
} 