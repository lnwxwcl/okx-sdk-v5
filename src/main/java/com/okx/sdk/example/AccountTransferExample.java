package com.okx.sdk.example;

import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.config.OkxConfig;
import com.okx.sdk.model.account.FundsTransfer;
import com.okx.sdk.model.account.TransferState;
import com.okx.sdk.service.account.AccountService;
import com.okx.sdk.service.account.impl.AccountServiceImpl;

public class AccountTransferExample {
    public static void main(String[] args) {
        // 替换为您的API密钥信息
        String apiKey = "";
        String secretKey = "";
        String passphrase = "";
        AccountService accountService = new AccountServiceImpl(apiKey, secretKey, passphrase, OkxConfig.BASE_URL);

        try {
            // 使用正确格式的clientId：字母和数字的组合，长度在1-32位之间
            String clientId = "TRANSFER" + System.currentTimeMillis();
            // 执行资金划转
            System.out.println("执行资金划转...");
            FundsTransfer transfer = FundsTransfer.builder()
                    .type("1")  // 母账户转子账户
                    .ccy("USDT")
                    .amt("1")
                    .from("18")  // 从交易账户
                    .to("18")   // 到交易账户
                    .subAcct("ClingSub2")  // 子账户名称
                    .build();

            OkxResponse<String> transferResponse = accountService.fundsTransfer(transfer);
            if (transferResponse.isSuccessful()) {
                String transId = transferResponse.getData();
                System.out.println("划转成功，划转ID: " + transId);

                // 等待一秒，让划转状态更新
                Thread.sleep(1000);

                // 查询划转状态
                System.out.println("\n查询划转状态...");
                OkxResponse<TransferState> stateResponse = accountService.getTransferState(null, clientId,"1");
                if (stateResponse.isSuccessful()) {
                    TransferState state = stateResponse.getData();
                    if (state != null) {
                        System.out.println("划转状态: " + state.getState());
                        System.out.println("币种: " + state.getCcy());
                        System.out.println("数量: " + state.getAmt());
                        System.out.println("时间: " + state.getTs());
                    } else {
                        System.out.println("未找到划转记录");
                    }
                } else {
                    System.out.println("查询划转状态失败: " + stateResponse.getMsg());
                }
            } else {
                System.out.println("划转失败: " + transferResponse.getMsg());
            }

        } catch (Exception e) {
            System.err.println("发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}