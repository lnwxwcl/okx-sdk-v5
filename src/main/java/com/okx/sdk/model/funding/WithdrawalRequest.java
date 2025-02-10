package com.okx.sdk.model.funding;

import lombok.Builder;
import lombok.Data;

/**
 * 提现请求参数
 */
@Data
@Builder
public class WithdrawalRequest {
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
     * 提现地址
     */
    private String addr;
    
    /**
     * 提现方式
     * 3：内部转账
     * 4：链上提币
     */
    private String fee;
    
    /**
     * 提现地址标签
     */
    private String tag;
    
    /**
     * 提现地址备注
     */
    private String memo;
    
    /**
     * 客户自定义提现ID
     */
    private String clientId;
    
    /**
     * 密码
     */
    private String pwd;
} 