package com.okx.sdk.service.account;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.account.Account;
import com.okx.sdk.model.account.Bill;
import com.okx.sdk.model.account.Position;

import java.io.IOException;
import java.util.List;

/**
 * OKX 账户服务接口
 */
public interface AccountService {
    /**
     * 查看账户余额
     *
     * @param ccy 币种，为空则返回所有币种余额
     * @return 账户信息
     */
    OkxResponse<Account> getBalance(String ccy) throws IOException;

    /**
     * 查看持仓信息
     *
     * @param instType 产品类型
     * @param instId   产品ID
     * @param posId    持仓ID
     * @return 持仓信息列表
     */
    OkxResponse<List<Position>> getPositions(String instType, String instId, String posId) throws IOException;

    /**
     * 查看账单流水
     *
     * @param instType 产品类型
     * @param ccy      币种
     * @param mgnMode  保证金模式
     * @param ctType   合约类型
     * @param type     账单类型
     * @param subType  账单子类型
     * @param after    请求此ID之前（更旧的数据）的分页内容
     * @param before   请求此ID之后（更新的数据）的分页内容
     * @param limit    分页返回的结果集数量，最大为100，默认100
     * @return 账单列表
     */
    OkxResponse<List<Bill>> getBills(String instType, String ccy, String mgnMode, String ctType,
                                   String type, String subType, String after, String before, Integer limit) throws IOException;

    /**
     * 设置杠杆倍数
     *
     * @param instId   产品ID
     * @param lever    杠杆倍数
     * @param mgnMode  保证金模式
     * @param posSide  持仓方向
     * @return 设置结果
     */
    OkxResponse<Void> setLeverage(String instId, String lever, String mgnMode, String posSide) throws IOException;

    /**
     * 获取最大可交易数量
     *
     * @param instId   产品ID
     * @param tdMode   交易模式
     * @param ccy      保证金币种
     * @param px       委托价格
     * @param leverage 杠杆倍数
     * @return 最大可交易数量
     */
    OkxResponse<String> getMaxSize(String instId, String tdMode, String ccy, String px, String leverage) throws IOException;

    /**
     * 获取最大可用余额
     *
     * @param instId   产品ID
     * @param tdMode   交易模式
     * @param ccy      保证金币种
     * @param px       委托价格
     * @param leverage 杠杆倍数
     * @return 最大可用余额
     */
    OkxResponse<String> getMaxAvailable(String instId, String tdMode, String ccy, String px, String leverage) throws IOException;

    /**
     * 调整保证金
     *
     * @param instId   产品ID
     * @param posSide  持仓方向
     * @param type     调整方向
     * @param amt      调整数量
     * @param ccy      保证金币种
     * @return 调整结果
     */
    OkxResponse<Void> adjustMargin(String instId, String posSide, String type, String amt, String ccy) throws IOException;
} 