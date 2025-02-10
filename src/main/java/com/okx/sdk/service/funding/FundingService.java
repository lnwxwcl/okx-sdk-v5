package com.okx.sdk.service.funding;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.funding.DepositAddress;
import com.okx.sdk.model.funding.DepositHistory;
import com.okx.sdk.model.funding.WithdrawalHistory;
import com.okx.sdk.model.funding.WithdrawalRequest;

import java.io.IOException;
import java.util.List;

/**
 * OKX 资金服务接口
 */
public interface FundingService {
    /**
     * 获取充值地址
     *
     * @param ccy   币种
     * @param chain 链信息
     * @return 充值地址信息
     */
    OkxResponse<List<DepositAddress>> getDepositAddress(String ccy, String chain) throws IOException;

    /**
     * 获取充值记录
     *
     * @param ccy    币种
     * @param txId   交易ID
     * @param state  充值状态
     * @param after  请求此ID之前（更旧的数据）的分页内容
     * @param before 请求此ID之后（更新的数据）的分页内容
     * @param limit  分页返回的结果集数量，最大为100，默认100
     * @return 充值记录列表
     */
    OkxResponse<List<DepositHistory>> getDepositHistory(String ccy, String txId, String state,
                                                      String after, String before, Integer limit) throws IOException;

    /**
     * 提现
     *
     * @param request 提现请求参数
     * @return 提现ID
     */
    OkxResponse<String> withdrawal(WithdrawalRequest request) throws IOException;

    /**
     * 撤销提现
     *
     * @param wdId 提现ID
     * @return 撤销结果
     */
    OkxResponse<Void> cancelWithdrawal(String wdId) throws IOException;

    /**
     * 获取提现记录
     *
     * @param ccy    币种
     * @param wdId   提现ID
     * @param txId   交易ID
     * @param state  提现状态
     * @param after  请求此ID之前（更旧的数据）的分页内容
     * @param before 请求此ID之后（更新的数据）的分页内容
     * @param limit  分页返回的结果集数量，最大为100，默认100
     * @return 提现记录列表
     */
    OkxResponse<List<WithdrawalHistory>> getWithdrawalHistory(String ccy, String wdId, String txId, String state,
                                                            String after, String before, Integer limit) throws IOException;

    /**
     * 获取币种列表
     *
     * @param ccy 币种
     * @return 币种信息列表
     */
    OkxResponse<List<String>> getCurrencies(String ccy) throws IOException;

    /**
     * 闪电网络充值
     *
     * @param ccy      币种
     * @param invoice  发票
     * @param memo     备注
     * @return 充值结果
     */
    OkxResponse<Void> lightningDeposit(String ccy, String invoice, String memo) throws IOException;

    /**
     * 闪电网络提现
     *
     * @param ccy      币种
     * @param invoice  发票
     * @param memo     备注
     * @return 提现结果
     */
    OkxResponse<Void> lightningWithdrawal(String ccy, String invoice, String memo) throws IOException;
} 