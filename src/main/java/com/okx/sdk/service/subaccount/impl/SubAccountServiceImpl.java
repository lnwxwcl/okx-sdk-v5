package com.okx.sdk.service.subaccount.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.JSONObject;
import com.okx.sdk.client.OkxRestClient;
import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.subaccount.*;
import com.okx.sdk.service.subaccount.SubAccountService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 子账户服务实现类
 */
public class SubAccountServiceImpl extends OkxRestClient implements SubAccountService {

    public SubAccountServiceImpl(String apiKey, String secretKey, String passphrase, String baseUrl) {
        super(apiKey, secretKey, passphrase, baseUrl);
    }

    @Override
    public OkxResponse<List<SubAccount>> getSubAccountList(Boolean enable, String subAcct, 
            String after, String before, Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        if (enable != null) {
            params.add("enable=" + enable);
        }
        if (StringUtils.isNotEmpty(subAcct)) {
            params.add("subAcct=" + subAcct);
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
        String response = get("/api/v5/users/subaccount/list", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<SubAccount>>>() {});
    }

    @Override
    public OkxResponse<List<SubAccountBalance>> getSubAccountBalance(String subAcct, String ccy) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("subAcct=" + subAcct);
        if (StringUtils.isNotEmpty(ccy)) {
            params.add("ccy=" + ccy);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/account/subaccount/balances", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<SubAccountBalance>>>() {});
    }

    @Override
    public OkxResponse<String> subAccountTransfer(SubAccountTransfer transfer) throws IOException {
        String response = post("/api/v5/account/subaccount/transfer", transfer);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<String> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        if (jsonObject.getJSONArray("data") != null && !jsonObject.getJSONArray("data").isEmpty()) {
            result.setData(jsonObject.getJSONArray("data").getJSONObject(0).getString("transId"));
        }
        
        return result;
    }

    @Override
    public OkxResponse<Void> setTransferOut(String subAcct, Boolean canTransOut) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("subAcct", subAcct);
        params.put("canTransOut", canTransOut);

        String response = post("/api/v5/users/subaccount/set-transfer-out", params);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Void>>() {});
    }

    @Override
    public OkxResponse<List<SubAccountBalance>> getFundingBalance(String subAcct) throws IOException {
        String queryString = "subAcct=" + subAcct;
        String response = get("/api/v5/asset/subaccount/balances", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<SubAccountBalance>>>() {});
    }
} 