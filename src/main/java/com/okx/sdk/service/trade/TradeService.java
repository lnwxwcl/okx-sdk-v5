package com.okx.sdk.service.trade;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.trade.Order;
import com.okx.sdk.model.trade.PlaceOrderRequest;

import java.io.IOException;
import java.util.List;

/**
 * OKX 交易服务接口
 */
public interface TradeService {
    /**
     * 下单
     *
     * @param request 下单请求参数
     * @return 订单信息
     */
    OkxResponse<Order> placeOrder(PlaceOrderRequest request) throws IOException;

    /**
     * 批量下单
     *
     * @param requests 批量下单请求参数
     * @return 订单信息列表
     */
    OkxResponse<List<Order>> placeMultipleOrders(List<PlaceOrderRequest> requests) throws IOException;

    /**
     * 撤销指定订单
     *
     * @param instId  产品ID
     * @param ordId   订单ID
     * @param clOrdId 客户自定义订单ID
     * @return 撤销结果
     */
    OkxResponse<Order> cancelOrder(String instId, String ordId, String clOrdId) throws IOException;

    /**
     * 批量撤销订单
     *
     * @param instId   产品ID
     * @param ordIds   订单ID列表
     * @param clOrdIds 客户自定义订单ID列表
     * @return 撤销结果列表
     */
    OkxResponse<List<Order>> cancelMultipleOrders(String instId, List<String> ordIds, List<String> clOrdIds) throws IOException;

    /**
     * 修改订单
     *
     * @param instId     产品ID
     * @param ordId      订单ID
     * @param clOrdId    客户自定义订单ID
     * @param reqId      用户自定义修改事件ID
     * @param newSize    新的委托数量
     * @param newPrice   新的委托价格
     * @return 修改结果
     */
    OkxResponse<Order> amendOrder(String instId, String ordId, String clOrdId, String reqId, String newSize, String newPrice) throws IOException;

    /**
     * 获取订单信息
     *
     * @param instId  产品ID
     * @param ordId   订单ID
     * @param clOrdId 客户自定义订单ID
     * @return 订单信息
     */
    OkxResponse<Order> getOrderDetails(String instId, String ordId, String clOrdId) throws IOException;

    /**
     * 获取未成交订单列表
     *
     * @param instType 产品类型
     * @param instId   产品ID
     * @param ordType  订单类型
     * @param state    订单状态
     * @param after    请求此ID之前（更旧的数据）的分页内容
     * @param before   请求此ID之后（更新的数据）的分页内容
     * @param limit    返回结果的数量，最大为100，默认100
     * @return 订单列表
     */
    OkxResponse<List<Order>> getPendingOrders(String instType, String instId, String ordType, String state,
                                            String after, String before, Integer limit) throws IOException;

    /**
     * 获取历史订单记录（近七天）
     *
     * @param instType 产品类型
     * @param instId   产品ID
     * @param ordType  订单类型
     * @param state    订单状态
     * @param after    请求此ID之前（更旧的数据）的分页内容
     * @param before   请求此ID之后（更新的数据）的分页内容
     * @param limit    返回结果的数量，最大为100，默认100
     * @return 订单列表
     */
    OkxResponse<List<Order>> getOrderHistory(String instType, String instId, String ordType, String state,
                                          String after, String before, Integer limit) throws IOException;
}