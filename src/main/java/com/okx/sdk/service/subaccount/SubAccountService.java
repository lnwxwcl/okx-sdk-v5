package com.okx.sdk.service.subaccount;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.subaccount.SubAccount;
import com.okx.sdk.model.subaccount.SubAccountBalance;
import com.okx.sdk.model.subaccount.SubAccountTransfer;

import java.io.IOException;
import java.util.List;

/**
 * OKX 子账户服务接口
 */
public interface SubAccountService {
    /**
     * 查看子账户列表
     *
     * @param enable 是否启用
     * @param subAcct 子账户名称
     * @param after 请求此ID之前（更旧的数据）的分页内容
     * @param before 请求此ID之后（更新的数据）的分页内容
     * @param limit 分页返回的结果集数量，最大为100，默认100
     * @return 子账户列表
     */
    OkxResponse<List<SubAccount>> getSubAccountList(Boolean enable, String subAcct, 
            String after, String before, Integer limit) throws IOException;

    /**
     * 获取子账户资金余额
     *
     * @param subAcct 子账户名称
     * @param ccy 币种，为空则返回所有币种余额
     * @return 子账户资金信息
     */
    OkxResponse<List<SubAccountBalance>> getSubAccountBalance(String subAcct, String ccy) throws IOException;

    /**
     * 子账户间资金转移
     *
     * @param transfer 转账请求参数
     * @return 转账ID
     */
    OkxResponse<String> subAccountTransfer(SubAccountTransfer transfer) throws IOException;

    /**
     * 设置子账户主动转出权限
     *
     * @param subAcct 子账户名称
     * @param canTransOut 是否可以主动转出
     * @return 设置结果
     */
    OkxResponse<Void> setTransferOut(String subAcct, Boolean canTransOut) throws IOException;

    /**
     * 查询子账户资金账户余额
     *
     * @param subAcct 子账户名称
     * @return 子账户资金账户余额
     */
    OkxResponse<List<SubAccountBalance>> getFundingBalance(String subAcct) throws IOException;
} 