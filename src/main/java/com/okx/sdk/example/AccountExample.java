package com.okx.sdk.example;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.config.OkxConfig;
import com.okx.sdk.model.account.Account;
import com.okx.sdk.model.account.Bill;
import com.okx.sdk.model.account.Position;
import com.okx.sdk.service.account.AccountService;
import com.okx.sdk.service.account.impl.AccountServiceImpl;

import java.util.List;


/**
 * OKX API 账户接口调用示例
 */
public class AccountExample {
    public static void main(String[] args) {
        // 替换为您的API密钥信息
        String apiKey = "";
        String secretKey = "";
        String passphrase = "";

        // 创建账户服务实例
        AccountService accountService = new AccountServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);
        try {
            // 查询账户余额
            OkxResponse<Account> balanceResponse = accountService.getBalance("");
            if (balanceResponse.isSuccessful()) {
                Account account = balanceResponse.getData();
                if (account != null && account.getDetails() != null) {
                    System.out.println("账户总权益: " + account.getTotalEq() + " USTD");
                    System.out.println("\n币种余额信息:");
                    for (Account.Balance balance : account.getDetails()) {
                        System.out.printf("币种: %s, 可用余额: %s, 冻结余额: %s%n",balance.getCcy(), balance.getAvailBal(), balance.getFrozenBal());
                    }
                }
            }

            // 查询持仓信息
            System.out.println("\n查询持仓信息...");
            OkxResponse<List<Position>> positionsResponse = accountService.getPositions("MARGIN", "BTC-USDT", null);
            if (positionsResponse.isSuccessful()) {
                if (!positionsResponse.getData().isEmpty()) {
                    System.out.println("持仓信息:");
                    for (Position position : positionsResponse.getData()) {
                        System.out.printf("产品: %s, 持仓方向: %s, 持仓数量: %s, 开仓均价: %s, 未实现盈亏: %s%n",
                                position.getInstId(), position.getPosSide(), position.getPos(),
                                position.getAvgPx(), position.getUpl());
                    }
                } else {
                    System.out.println("没有持仓信息");
                }
            } else {
                System.out.println("查询持仓信息失败: " + positionsResponse.getMsg());
            }

            // 查询账单流水
            System.out.println("\n查询账单流水...");
            OkxResponse<List<Bill>> billsResponse = accountService.getBills(null, null, null,
                    null, null, null, null, null, 5);
            if (billsResponse.isSuccessful()) {
                List<Bill> bills = billsResponse.getData();
                if (!bills.isEmpty()) {
                    System.out.println("最近5条账单记录:");
                    for (Bill bill : bills) {
                        System.out.printf("账单ID: %s, 币种: %s, 变动数量: %s, 余额: %s, 类型: %s, 时间: %s%n",
                                bill.getBillId(), bill.getCcy(), bill.getBalChg(), bill.getBal(),
                                bill.getType(), bill.getTs());
                    }
                } else {
                    System.out.println("没有账单记录");
                }
            } else {
                System.out.println("查询账单流水失败: " + billsResponse.getMsg());
            }

            // 设置全仓杠杆倍数
            System.out.println("\n设置全仓杠杆倍数...");
            try {
                OkxResponse<Void> crossLeverageResponse = accountService.setLeverage("BTC-USDT", "5",
                        "cross", null);
                if (crossLeverageResponse.isSuccessful()) {
                    System.out.println("设置全仓杠杆倍数成功");
                } else {
                    System.out.println("设置全仓杠杆倍数失败: " + crossLeverageResponse.getMsg() + " (错误码: " + crossLeverageResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("设置全仓杠杆倍数时发生错误: " + e.getMessage());
            }

            // 设置逐仓杠杆倍数
            System.out.println("\n设置逐仓杠杆倍数...");
            try {
                // 设置多头方向的逐仓杠杆
                OkxResponse<Void> isolatedLongResponse = accountService.setLeverage("BTC-USDT", "5",
                        "isolated", "long");
                if (isolatedLongResponse.isSuccessful()) {
                    System.out.println("设置多头逐仓杠杆倍数成功");
                } else {
                    System.out.println("设置多头逐仓杠杆倍数失败: " + isolatedLongResponse.getMsg() + " (错误码: " + isolatedLongResponse.getCode() + ")");
                }

                // 设置空头方向的逐仓杠杆
                OkxResponse<Void> isolatedShortResponse = accountService.setLeverage("BTC-USDT", "5",
                        "isolated", "short");
                if (isolatedShortResponse.isSuccessful()) {
                    System.out.println("设置空头逐仓杠杆倍数成功");
                } else {
                    System.out.println("设置空头逐仓杠杆倍数失败: " + isolatedShortResponse.getMsg() + " (错误码: " + isolatedShortResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("设置逐仓杠杆倍数时发生错误: " + e.getMessage());
            }

            // 获取全仓最大可交易数量
            System.out.println("\n获取全仓最大可交易数量...");
            try {
                OkxResponse<String> crossMaxSizeResponse = accountService.getMaxSize("BTC-USDT",
                        "cross", "USDT", "40000", "5");
                if (crossMaxSizeResponse.isSuccessful()) {
                    if (crossMaxSizeResponse.getData() != null) {
                        System.out.println("全仓最大可交易数量: " + crossMaxSizeResponse.getData());
                    } else {
                        System.out.println("获取全仓最大可交易数量失败: 返回数据为空");
                    }
                } else {
                    System.out.println("获取全仓最大可交易数量失败: " + crossMaxSizeResponse.getMsg() + " (错误码: " + crossMaxSizeResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("获取全仓最大可交易数量时发生错误: " + e.getMessage());
            }

            // 获取逐仓最大可交易数量
            System.out.println("\n获取逐仓最大可交易数量...");
            try {
                // 获取多头方向的最大可交易数量
                OkxResponse<String> isolatedLongMaxSizeResponse = accountService.getMaxSize("BTC-USDT",
                        "isolated", "USDT", "40000", "5");
                if (isolatedLongMaxSizeResponse.isSuccessful()) {
                    if (isolatedLongMaxSizeResponse.getData() != null) {
                        System.out.println("多头逐仓最大可交易数量: " + isolatedLongMaxSizeResponse.getData());
                    } else {
                        System.out.println("获取多头逐仓最大可交易数量失败: 返回数据为空");
                    }
                } else {
                    System.out.println("获取多头逐仓最大可交易数量失败: " + isolatedLongMaxSizeResponse.getMsg() + " (错误码: " + isolatedLongMaxSizeResponse.getCode() + ")");
                }

                // 获取空头方向的最大可交易数量
                OkxResponse<String> isolatedShortMaxSizeResponse = accountService.getMaxSize("BTC-USDT",
                        "isolated", "USDT", "40000", "5");
                if (isolatedShortMaxSizeResponse.isSuccessful()) {
                    if (isolatedShortMaxSizeResponse.getData() != null) {
                        System.out.println("空头逐仓最大可交易数量: " + isolatedShortMaxSizeResponse.getData());
                    } else {
                        System.out.println("获取空头逐仓最大可交易数量失败: 返回数据为空");
                    }
                } else {
                    System.out.println("获取空头逐仓最大可交易数量失败: " + isolatedShortMaxSizeResponse.getMsg() + " (错误码: " + isolatedShortMaxSizeResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("获取逐仓最大可交易数量时发生错误: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}