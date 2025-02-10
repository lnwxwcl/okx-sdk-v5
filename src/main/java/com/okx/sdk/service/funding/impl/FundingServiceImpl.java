package com.okx.sdk.service.funding.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.okx.sdk.client.OkxRestClient;
import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.funding.DepositAddress;
import com.okx.sdk.model.funding.DepositHistory;
import com.okx.sdk.model.funding.WithdrawalHistory;
import com.okx.sdk.model.funding.WithdrawalRequest;
import com.okx.sdk.service.funding.FundingService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金服务实现类
 */
public class FundingServiceImpl extends OkxRestClient implements FundingService {

    public FundingServiceImpl(String apiKey, String secretKey, String passphrase, String baseUrl) {
        super(apiKey, secretKey, passphrase, baseUrl);
    }

    @Override
    public OkxResponse<List<DepositAddress>> getDepositAddress(String ccy, String chain) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(ccy)) {
            params.add("ccy=" + ccy);
        }
        if (StringUtils.isNotEmpty(chain)) {
            params.add("chain=" + chain);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/asset/deposit-address", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<DepositAddress>>>() {});
    }

    @Override
    public OkxResponse<List<DepositHistory>> getDepositHistory(String ccy, String txId, String state,
                                                            String after, String before, Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(ccy)) {
            params.add("ccy=" + ccy);
        }
        if (StringUtils.isNotEmpty(txId)) {
            params.add("txId=" + txId);
        }
        if (StringUtils.isNotEmpty(state)) {
            params.add("state=" + state);
        }
        if (StringUtils.isNotEmpty(after)) {
            params.add("after=" + after);
        }
        if (StringUtils.isNotEmpty(before)) {
            params.add("before=" + before);
        }
        if (limit != null) {
            params.add("limit=" + limit);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/asset/deposit-history", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<DepositHistory>>>() {});
    }

    @Override
    public OkxResponse<String> withdrawal(WithdrawalRequest request) throws IOException {
        String response = post("/api/v5/asset/withdrawal", request);
        return JSON.parseObject(response, new TypeReference<OkxResponse<String>>() {});
    }

    @Override
    public OkxResponse<Void> cancelWithdrawal(String wdId) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("wdId", wdId);

        String response = post("/api/v5/asset/cancel-withdrawal", params);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Void>>() {});
    }

    @Override
    public OkxResponse<List<WithdrawalHistory>> getWithdrawalHistory(String ccy, String wdId, String txId, String state,
                                                                  String after, String before, Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(ccy)) {
            params.add("ccy=" + ccy);
        }
        if (StringUtils.isNotEmpty(wdId)) {
            params.add("wdId=" + wdId);
        }
        if (StringUtils.isNotEmpty(txId)) {
            params.add("txId=" + txId);
        }
        if (StringUtils.isNotEmpty(state)) {
            params.add("state=" + state);
        }
        if (StringUtils.isNotEmpty(after)) {
            params.add("after=" + after);
        }
        if (StringUtils.isNotEmpty(before)) {
            params.add("before=" + before);
        }
        if (limit != null) {
            params.add("limit=" + limit);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/asset/withdrawal-history", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<WithdrawalHistory>>>() {});
    }

    @Override
    public OkxResponse<List<String>> getCurrencies(String ccy) throws IOException {
        String path = "/api/v5/asset/currencies";
        if (StringUtils.isNotEmpty(ccy)) {
            path += "?ccy=" + ccy;
        }
        String response = get(path, null);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<String>>>() {});
    }

    @Override
    public OkxResponse<Void> lightningDeposit(String ccy, String invoice, String memo) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("ccy", ccy);
        params.put("invoice", invoice);
        if (StringUtils.isNotEmpty(memo)) {
            params.put("memo", memo);
        }

        String response = post("/api/v5/asset/lightning/deposit", params);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Void>>() {});
    }

    @Override
    public OkxResponse<Void> lightningWithdrawal(String ccy, String invoice, String memo) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("ccy", ccy);
        params.put("invoice", invoice);
        if (StringUtils.isNotEmpty(memo)) {
            params.put("memo", memo);
        }

        String response = post("/api/v5/asset/lightning/withdrawal", params);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Void>>() {});
    }
} 