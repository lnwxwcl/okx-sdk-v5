package com.okx.sdk.example;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.config.OkxConfig;
import com.okx.sdk.model.subaccount.*;
import com.okx.sdk.service.subaccount.SubAccountService;
import com.okx.sdk.service.subaccount.impl.SubAccountServiceImpl;

import java.util.List;

/**
 * OKX API 子账户接口调用示例
 */
public class SubAccountExample {
    public static void main(String[] args) {
        // 替换为您的API密钥信息
        String apiKey = "";
        String secretKey = "";
        String passphrase = "";

        // 创建子账户服务实例
        SubAccountService subAccountService = new SubAccountServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);

        try {
            // 查询子账户列表
            System.out.println("查询子账户列表...");
            OkxResponse<List<SubAccount>> subAccountsResponse = subAccountService.getSubAccountList(true, null, null, null, 10);
            if (subAccountsResponse.isSuccessful()) {
                List<SubAccount> subAccounts = subAccountsResponse.getData();
                if (subAccounts != null && !subAccounts.isEmpty()) {
                    System.out.println("子账户列表:");
                    for (SubAccount subAccount : subAccounts) {
                        System.out.printf("账户名: %s, 类型: %s, 备注: %s, 创建时间: %s%n",
                                subAccount.getSubAcct(), subAccount.getType(),
                                subAccount.getLabel(), subAccount.getTs());
                    }
                } else {
                    System.out.println("没有子账户");
                }
            } else {
                System.out.println("查询子账户列表失败: " + subAccountsResponse.getMsg());
            }

            // 如果有子账户，查询第一个子账户的余额
            if (subAccountsResponse.isSuccessful() && subAccountsResponse.getData() != null
                    && !subAccountsResponse.getData().isEmpty()) {
                String subAcct = subAccountsResponse.getData().get(0).getSubAcct();

                // 查询子账户交易账户余额
                System.out.println("\n查询子账户交易账户余额...");
                OkxResponse<List<SubAccountBalance>> balanceResponse =
                        subAccountService.getSubAccountBalance(subAcct, null);
                if (balanceResponse.isSuccessful()) {
                    List<SubAccountBalance> balances = balanceResponse.getData();
                    if (balances != null && !balances.isEmpty()) {
                        System.out.println("子账户交易账户余额信息:");
                        for (SubAccountBalance balance : balances) {
                            System.out.printf("币种: %s, 可用余额: %s, 冻结余额: %s%n",
                                    balance.getCcy(), balance.getAvailBal(), balance.getFrozenBal());
                        }
                    }
                } else {
                    System.out.println("查询子账户交易账户余额失败: " + balanceResponse.getMsg());
                }

                // 查询子账户资金账户余额
                System.out.println("\n查询子账户资金账户余额...");
                OkxResponse<List<SubAccountBalance>> fundingResponse =
                        subAccountService.getFundingBalance(subAcct);
                if (fundingResponse.isSuccessful()) {
                    List<SubAccountBalance> balances = fundingResponse.getData();
                    if (balances != null && !balances.isEmpty()) {
                        System.out.println("子账户资金账户余额信息:");
                        for (SubAccountBalance balance : balances) {
                            System.out.printf("币种: %s, 可用余额: %s, 冻结余额: %s%n",
                                    balance.getCcy(), balance.getAvailBal(), balance.getFrozenBal());
                        }
                    }
                } else {
                    System.out.println("查询子账户资金账户余额失败: " + fundingResponse.getMsg());
                }

                // 子账户间转账示例
                System.out.println("\n执行子账户间转账...");
                SubAccountTransfer transfer = SubAccountTransfer.builder()
                        .ccy("USDT")
                        .amt("1")
                        .from("18")
                        .to("18")
                        .fromSubAccount("85687550118862848")
                        .toSubAccount("ClingSub2")
                        .build();

                try {
                    OkxResponse<String> transferResponse = subAccountService.subAccountTransfer(transfer);
                    if (transferResponse.isSuccessful()) {
                        System.out.println("转账成功，转账ID: " + transferResponse.getData());
                    } else {
                        System.out.println("转账失败: " + transferResponse.getMsg());
                    }
                } catch (Exception e) {
                    System.out.println("转账发生错误: " + e.getMessage());
                }
//
//                // 设置子账户主动转出权限
//                System.out.println("\n设置子账户主动转出权限...");
//                try {
//                    OkxResponse<Void> setTransferOutResponse =
//                            subAccountService.setTransferOut(subAcct, true);
//                    if (setTransferOutResponse.isSuccessful()) {
//                        System.out.println("设置子账户主动转出权限成功");
//                    } else {
//                        System.out.println("设置子账户主动转出权限失败: " + setTransferOutResponse.getMsg());
//                    }
//                } catch (Exception e) {
//                    System.out.println("设置子账户主动转出权限时发生错误: " + e.getMessage());
//                }
            }

        } catch (Exception e) {
            System.err.println("发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 