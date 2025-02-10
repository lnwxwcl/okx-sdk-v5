package com.okx.sdk.example;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.config.OkxConfig;
import com.okx.sdk.model.funding.DepositAddress;
import com.okx.sdk.model.funding.DepositHistory;
import com.okx.sdk.model.funding.WithdrawalHistory;
import com.okx.sdk.model.funding.WithdrawalRequest;
import com.okx.sdk.service.funding.FundingService;
import com.okx.sdk.service.funding.impl.FundingServiceImpl;

import java.util.List;

/**
 * OKX API 资金接口调用示例
 */
public class FundingExample {
    public static void main(String[] args) {
        // 替换为您的API密钥信息
        String apiKey = "YOUR-API-KEY";
        String secretKey = "YOUR-SECRET-KEY";
        String passphrase = "YOUR-PASSPHRASE";

        // 创建资金服务实例
        FundingService fundingService = new FundingServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);

        try {
            // 获取币种列表
            System.out.println("获取币种列表...");
            try {
                OkxResponse<List<String>> currenciesResponse = fundingService.getCurrencies(null);
                if (currenciesResponse.isSuccessful()) {
                    System.out.println("支持的币种列表:");
                    System.out.println(String.join(", ", currenciesResponse.getData()));
                } else {
                    System.out.println("获取币种列表失败: " + currenciesResponse.getMsg() + " (错误码: " + currenciesResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("获取币种列表时发生错误: " + e.getMessage());
            }

            // 获取充值记录
            System.out.println("\n获取充值记录...");
            try {
                OkxResponse<List<DepositHistory>> depositResponse = fundingService.getDepositHistory("USDT",
                        null, null, null, null, 5);
                if (depositResponse.isSuccessful()) {
                    List<DepositHistory> deposits = depositResponse.getData();
                    if (deposits != null && !deposits.isEmpty()) {
                        System.out.println("最近5条充值记录:");
                        for (DepositHistory deposit : deposits) {
                            System.out.printf("币种: %s, 数量: %s, 状态: %s, 时间: %s%n",
                                    deposit.getCcy(), deposit.getAmt(), deposit.getState(), deposit.getTs());
                        }
                    } else {
                        System.out.println("没有充值记录");
                    }
                } else {
                    System.out.println("获取充值记录失败: " + depositResponse.getMsg() + " (错误码: " + depositResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("获取充值记录时发生错误: " + e.getMessage());
            }

            // 获取充值地址
            System.out.println("\n获取充值地址...");
            try {
                OkxResponse<List<DepositAddress>> addressResponse = fundingService.getDepositAddress("USDT", "USDT-TRC20");
                if (addressResponse.isSuccessful()) {
                    List<DepositAddress> addresses = addressResponse.getData();
                    if (addresses != null && !addresses.isEmpty()) {
                        System.out.println("USDT充值地址信息:");
                        for (DepositAddress address : addresses) {
                            System.out.printf("地址: %s, 链: %s, 标签: %s%n",
                                    address.getAddr(), address.getChain(), address.getTag());
                        }
                    } else {
                        System.out.println("没有充值地址信息");
                    }
                } else {
                    System.out.println("获取充值地址失败: " + addressResponse.getMsg() + " (错误码: " + addressResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("获取充值地址时发生错误: " + e.getMessage());
            }

            // 获取提现记录
            System.out.println("\n获取提现记录...");
            try {
                OkxResponse<List<WithdrawalHistory>> withdrawalHistoryResponse = fundingService
                        .getWithdrawalHistory("USDT", null, null, null,
                                null, null, null);
                if (withdrawalHistoryResponse.isSuccessful()) {
                    List<WithdrawalHistory> histories = withdrawalHistoryResponse.getData();
                    if (histories != null && !histories.isEmpty()) {
                        System.out.println("提现记录:");
                        for (WithdrawalHistory history : histories) {
                            System.out.printf("提现ID: %s, 币种: %s, 数量: %s, 状态: %s, 时间: %s%n",
                                    history.getWdId(), history.getCcy(), history.getAmt(),
                                    history.getState(), history.getTs());
                        }
                    } else {
                        System.out.println("没有提现记录");
                    }
                } else {
                    System.out.println("获取提现记录失败: " + withdrawalHistoryResponse.getMsg() + " (错误码: " + withdrawalHistoryResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("获取提现记录时发生错误: " + e.getMessage());
            }

            // 注意：以下操作需要资金操作权限
            System.out.println("\n注意：以下操作需要资金操作权限，请确保您的API key具有相应权限");

            // 提现示例（请谨慎测试，确保地址正确）
            System.out.println("\n提现示例（已注释，请根据需要打开）...");
            /*
            try {
                WithdrawalRequest withdrawalRequest = WithdrawalRequest.builder()
                        .ccy("USDT")
                        .chain("USDT-TRC20")
                        .amt("1")  // 最小提现数量
                        .addr("YOUR-USDT-ADDRESS")  // 替换为您的提现地址
                        .fee("0.8")  // TRC20网络提现费用
                        .build();

                OkxResponse<String> withdrawalResponse = fundingService.withdrawal(withdrawalRequest);
                if (withdrawalResponse.isSuccessful()) {
                    String wdId = withdrawalResponse.getData();
                    System.out.println("提现申请成功，提现ID: " + wdId);
                } else {
                    System.out.println("提现申请失败: " + withdrawalResponse.getMsg() + " (错误码: " + withdrawalResponse.getCode() + ")");
                }
            } catch (Exception e) {
                System.out.println("提现申请时发生错误: " + e.getMessage());
            }
            */

        } catch (Exception e) {
            System.err.println("程序执行过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 