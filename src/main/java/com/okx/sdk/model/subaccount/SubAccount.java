package com.okx.sdk.model.subaccount;

import lombok.Data;

/**
 * 子账户信息
 */
@Data
public class SubAccount {
    /**
     * 子账户名称
     */
    private String subAcct;
    
    /**
     * 子账户类型
     * 1：普通子账户
     * 2：托管子账户
     */
    private String type;
    
    /**
     * 是否启用
     */
    private Boolean enable;
    
    /**
     * 子账户备注
     */
    private String label;
    
    /**
     * 子账户资金账户ID
     */
    private String uid;
    
    /**
     * 子账户创建时间
     */
    private String ts;
} 