package com.okx.sdk.service.account.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.okx.sdk.client.OkxRestClient;
import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.account.Account;
import com.okx.sdk.model.account.Bill;
import com.okx.sdk.model.account.Position;
import com.okx.sdk.service.account.AccountService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户服务实现类
 */
public class AccountServiceImpl extends OkxRestClient implements AccountService {

    public AccountServiceImpl(String apiKey, String secretKey, String passphrase, String baseUrl) {
        super(apiKey, secretKey, passphrase, baseUrl);
    }

    @Override
    public OkxResponse<Account> getBalance(String ccy) throws IOException {
        String path = "/api/v5/account/balance";
        if (StringUtils.isNotEmpty(ccy)) {
            path += "?ccy=" + ccy;
        }
        String response = get(path, null);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<Account> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null && !dataArray.isEmpty()) {
            result.setData(dataArray.getObject(0, Account.class));
        }
        
        return result;
    }

    @Override
    public OkxResponse<List<Position>> getPositions(String instType, String instId, String posId) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(instId)) {
            params.add("instId=" + instId);
        }
        if (StringUtils.isNotEmpty(posId)) {
            params.add("posId=" + posId);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/account/positions", queryString);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<List<Position>> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null) {
            result.setData(dataArray.toList(Position.class));
        } else {
            result.setData(new ArrayList<>());
        }
        
        return result;
    }

    @Override
    public OkxResponse<List<Bill>> getBills(String instType, String ccy, String mgnMode, String ctType,
                                         String type, String subType, String after, String before, Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(ccy)) {
            params.add("ccy=" + ccy);
        }
        if (StringUtils.isNotEmpty(mgnMode)) {
            params.add("mgnMode=" + mgnMode);
        }
        if (StringUtils.isNotEmpty(ctType)) {
            params.add("ctType=" + ctType);
        }
        if (StringUtils.isNotEmpty(type)) {
            params.add("type=" + type);
        }
        if (StringUtils.isNotEmpty(subType)) {
            params.add("subType=" + subType);
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
        String response = get("/api/v5/account/bills", queryString);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<List<Bill>> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null) {
            result.setData(dataArray.toList(Bill.class));
        } else {
            result.setData(new ArrayList<>());
        }
        
        return result;
    }

    @Override
    public OkxResponse<Void> setLeverage(String instId, String lever, String mgnMode, String posSide) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("instId", instId);
        params.put("lever", lever);
        params.put("mgnMode", mgnMode);
        String ccy = instId.split("-")[1];
        params.put("ccy", ccy);
        if (StringUtils.isNotEmpty(posSide)) {
            params.put("posSide", posSide);
        }

        String response = post("/api/v5/account/set-leverage", params);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<Void> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        return result;
    }

    @Override
    public OkxResponse<String> getMaxSize(String instId, String tdMode, String ccy, String px, String leverage) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("instId=" + instId);
        params.add("tdMode=" + tdMode);
        if (StringUtils.isNotEmpty(ccy)) {
            params.add("ccy=" + ccy);
        }
        if (StringUtils.isNotEmpty(px)) {
            params.add("px=" + px);
        }
        if (StringUtils.isNotEmpty(leverage)) {
            params.add("leverage=" + leverage);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/account/max-size", queryString);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<String> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null && !dataArray.isEmpty()) {
            JSONObject dataObject = dataArray.getJSONObject(0);
            result.setData(dataObject.getString("maxSz"));
        }
        
        return result;
    }

    @Override
    public OkxResponse<String> getMaxAvailable(String instId, String tdMode, String ccy, String px, String leverage) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("instId=" + instId);
        params.add("tdMode=" + tdMode);
        if (StringUtils.isNotEmpty(ccy)) {
            params.add("ccy=" + ccy);
        }
        if (StringUtils.isNotEmpty(px)) {
            params.add("px=" + px);
        }
        if (StringUtils.isNotEmpty(leverage)) {
            params.add("leverage=" + leverage);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/account/max-avail-size", queryString);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<String> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null && !dataArray.isEmpty()) {
            JSONObject dataObject = dataArray.getJSONObject(0);
            result.setData(dataObject.getString("availSz"));
        }
        
        return result;
    }

    @Override
    public OkxResponse<Void> adjustMargin(String instId, String posSide, String type, String amt, String ccy) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("instId", instId);
        params.put("posSide", posSide);
        params.put("type", type);
        params.put("amt", amt);
        if (StringUtils.isNotEmpty(ccy)) {
            params.put("ccy", ccy);
        }

        String response = post("/api/v5/account/position/margin-balance", params);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<Void> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        return result;
    }
} 