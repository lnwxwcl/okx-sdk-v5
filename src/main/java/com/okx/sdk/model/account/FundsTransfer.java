package com.okx.sdk.model.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundsTransfer {
    /**
     * 划转类型
     * 0：账户内划转
     * 1：母账户转子账户(仅适用于母账户APIKey)
     * 2：子账户转母账户(仅适用于母账户APIKey)
     * 3：子账户转母账户(仅适用于子账户APIKey)
     * 4：子账户转子账户(仅适用于子账户APIKey，且目标账户需要是同一母账户下的其他子账户
     */
    private String type;
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
     * 6: 资金账户
     * 18: 交易账户
     */
    private String from;
    
    /**
     * 资金转入账户
     * 6: 资金账户
     * 18: 交易账户
     */
    private String to;
    
    /**
     * 子账户名称，适用于母账户控制子账户资金划转
     */
    private String subAcct;
    
    /**
     * 客户自定义ID
     * 字母（区分大小写）与数字的组合，可以是纯字母、纯数字且长度要在1-32位之间
     */
    private String clientId;
}