package com.okx.sdk.example;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.config.OkxConfig;
import com.okx.sdk.model.trade.Order;
import com.okx.sdk.model.trade.PlaceOrderRequest;
import com.okx.sdk.service.trade.TradeService;
import com.okx.sdk.service.trade.impl.TradeServiceImpl;
import com.okx.sdk.service.account.AccountService;
import com.okx.sdk.service.account.impl.AccountServiceImpl;
import com.okx.sdk.model.account.Account;

import java.util.List;

/**
 * OKX API 交易接口调用示例
 */
public class TradeExample {
    public static void main(String[] args) {
        // 替换为您的API密钥信息
        String apiKey = "";
        String secretKey = "";
        String passphrase = "";

        // 创建交易服务实例
        TradeService tradeService = new TradeServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);
        // 创建账户服务实例
        AccountService accountService = new AccountServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);

        try {
            // 先查询ETH余额
            System.out.println("查询ETH余额...");
            OkxResponse<Account> balanceResponse = accountService.getBalance("ETH");

            if (!balanceResponse.isSuccessful() || balanceResponse.getData() == null ||
                    balanceResponse.getData().getDetails() == null ||
                    balanceResponse.getData().getDetails().isEmpty()) {
                System.out.println("获取ETH余额失败");
                return;
            }

            String availableEth = balanceResponse.getData().getDetails().get(0).getAvailBal();
            System.out.println("可用ETH余额: " + availableEth);

            if (availableEth == null || Double.parseDouble(availableEth) <= 0) {
                System.out.println("没有可用的ETH余额");
                return;
            }

            // 市价卖出全部ETH
            PlaceOrderRequest sellRequest = PlaceOrderRequest.builder()
                    .instId("ORDI-USDT")
                    .tdMode("cash")
                    .side("sell")
                    .ordType("market")
                    .sz(availableEth)  // 卖出所有可用的ETH
                    .build();
//
//            System.out.println("正在下市价卖出订单，数量: " + availableEth + " ETH");
            // 也可以尝试市价买入示例
//            PlaceOrderRequest request = PlaceOrderRequest.builder()
//                    .instId("ORDI-USDT")
//                    .tdMode("cash")
//                    .side("buy")
//                    .ordType("market")
//                    .sz("20")  // 使用20 USDT购买ETH
//                    .build();
            OkxResponse<Order> placeOrderResponse = tradeService.placeOrder(null);
            if (placeOrderResponse.isSuccessful()) {
                if (placeOrderResponse.getData() != null) {
                    Order order = placeOrderResponse.getData();
                    System.out.println("下单成功，订单ID: " + order.getOrdId());
                    System.out.println("订单状态: " + order.getState());

                    // 等待一秒，让订单状态更新
                    Thread.sleep(1000);

                    // 查询订单详情
                    if (order.getOrdId() != null) {
                        OkxResponse<Order> orderResponse = tradeService.getOrderDetails("ETH-USDT", order.getOrdId(), null);
                        if (orderResponse.isSuccessful() && orderResponse.getData() != null) {
                            order = orderResponse.getData();
                            System.out.println("\n订单详情:");
                            System.out.println("订单ID: " + order.getOrdId());
                            System.out.println("产品ID: " + order.getInstId());
                            System.out.println("价格: " + order.getPx());
                            System.out.println("数量: " + order.getSz());
                            System.out.println("状态: " + order.getState());
                            System.out.println("创建时间: " + order.getCTime());
                        } else {
                            System.out.println("查询订单详情失败: " + orderResponse.getMsg() + " (错误码: " + orderResponse.getCode() + ")");
                        }
                    }
                } else {
                    System.out.println("下单成功，但未返回订单详情");
                }
            } else {
                System.out.println("下单失败: " + placeOrderResponse.getMsg() + " (错误码: " + placeOrderResponse.getCode() + ")");
            }

            // 等待一秒，让订单状态更新
            Thread.sleep(1000);

            // 获取未成交订单列表
            System.out.println("\n获取未成交订单列表...");
            OkxResponse<List<Order>> pendingOrdersResponse = tradeService.getPendingOrders("SPOT", "ETH-USDT", null, null, null, null, 10);
            if (pendingOrdersResponse.isSuccessful()) {
                List<Order> pendingOrders = pendingOrdersResponse.getData();
                if (pendingOrders != null && !pendingOrders.isEmpty()) {
                    System.out.println("未成交订单列表:");
                    for (Order order : pendingOrders) {
                        System.out.printf("订单ID: %s, 产品: %s, 价格: %s, 数量: %s, 状态: %s%n",
                                order.getOrdId(), order.getInstId(), order.getPx(), order.getSz(), order.getState());
                    }
                } else {
                    System.out.println("没有未成交的订单");
                }
            } else {
                System.out.println("获取未成交订单列表失败: " + pendingOrdersResponse.getMsg() + " (错误码: " + pendingOrdersResponse.getCode() + ")");
            }

        } catch (Exception e) {
            System.err.println("发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 