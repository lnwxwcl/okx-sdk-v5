package com.okx.sdk.service.trade.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.okx.sdk.client.OkxRestClient;
import com.okx.sdk.common.OkxResponse;
import com.okx.sdk.model.trade.Order;
import com.okx.sdk.model.trade.PlaceOrderRequest;
import com.okx.sdk.service.trade.TradeService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易服务实现类
 */
public class TradeServiceImpl extends OkxRestClient implements TradeService {

    public TradeServiceImpl(String apiKey, String secretKey, String passphrase, String baseUrl) {
        super(apiKey, secretKey, passphrase, baseUrl);
    }

    @Override
    public OkxResponse<Order> placeOrder(PlaceOrderRequest request) throws IOException {
        String response = post("/api/v5/trade/order", request);
        System.out.println("下单API响应: " + response); // 添加调试日志
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<Order> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null && !dataArray.isEmpty()) {
            JSONObject orderData = dataArray.getJSONObject(0);
            // 检查是否有错误码
            String sCode = orderData.getString("sCode");
            if (!"0".equals(sCode)) {
                // 如果sCode不为0，说明下单失败
                result.setCode(sCode);
                result.setMsg(orderData.getString("sMsg"));
            } else {
                // 下单成功，创建Order对象并设置所有可用字段
                Order order = new Order();
                order.setInstId(request.getInstId());
                order.setOrdId(orderData.getString("ordId"));
                order.setClOrdId(orderData.getString("clOrdId"));
                order.setTag(orderData.getString("tag"));
                order.setSide(request.getSide());
                order.setPx(request.getPx());
                order.setSz(request.getSz());
                order.setOrdType(request.getOrdType());
                order.setTdMode(request.getTdMode());
                // 市价单通常会立即成交
                if ("market".equals(request.getOrdType())) {
                    order.setState("filled");
                } else {
                    order.setState("live");
                }
                order.setCTime(orderData.getString("ts")); // 设置创建时间
                result.setData(order);
            }
        }
        
        return result;
    }

    @Override
    public OkxResponse<List<Order>> placeMultipleOrders(List<PlaceOrderRequest> requests) throws IOException {
        String response = post("/api/v5/trade/batch-orders", requests);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<List<Order>> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null) {
            List<Order> orders = new ArrayList<>();
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject orderData = dataArray.getJSONObject(i);
                if (!orderData.containsKey("sCode")) {
                    Order order = JSON.parseObject(orderData.toString(), Order.class);
                    orders.add(order);
                }
            }
            result.setData(orders);
        }
        
        return result;
    }

    @Override
    public OkxResponse<Order> cancelOrder(String instId, String ordId, String clOrdId) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("instId", instId);
        if (StringUtils.isNotEmpty(ordId)) {
            params.put("ordId", ordId);
        }
        if (StringUtils.isNotEmpty(clOrdId)) {
            params.put("clOrdId", clOrdId);
        }

        String response = post("/api/v5/trade/cancel-order", params);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Order>>() {});
    }

    @Override
    public OkxResponse<List<Order>> cancelMultipleOrders(String instId, List<String> ordIds, List<String> clOrdIds) throws IOException {
        List<Map<String, String>> params = new ArrayList<>();
        
        if (ordIds != null) {
            for (String ordId : ordIds) {
                Map<String, String> param = new HashMap<>();
                param.put("instId", instId);
                param.put("ordId", ordId);
                params.add(param);
            }
        }

        if (clOrdIds != null) {
            for (String clOrdId : clOrdIds) {
                Map<String, String> param = new HashMap<>();
                param.put("instId", instId);
                param.put("clOrdId", clOrdId);
                params.add(param);
            }
        }

        String response = post("/api/v5/trade/cancel-batch-orders", params);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Order>>>() {});
    }

    @Override
    public OkxResponse<Order> amendOrder(String instId, String ordId, String clOrdId, String reqId, String newSize, String newPrice) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("instId", instId);
        if (StringUtils.isNotEmpty(ordId)) {
            params.put("ordId", ordId);
        }
        if (StringUtils.isNotEmpty(clOrdId)) {
            params.put("clOrdId", clOrdId);
        }
        if (StringUtils.isNotEmpty(reqId)) {
            params.put("reqId", reqId);
        }
        if (StringUtils.isNotEmpty(newSize)) {
            params.put("newSz", newSize);
        }
        if (StringUtils.isNotEmpty(newPrice)) {
            params.put("newPx", newPrice);
        }

        String response = post("/api/v5/trade/amend-order", params);
        return JSON.parseObject(response, new TypeReference<OkxResponse<Order>>() {});
    }

    @Override
    public OkxResponse<Order> getOrderDetails(String instId, String ordId, String clOrdId) throws IOException {
        List<String> params = new ArrayList<>();
        params.add("instId=" + instId);
        if (StringUtils.isNotEmpty(ordId)) {
            params.add("ordId=" + ordId);
        }
        if (StringUtils.isNotEmpty(clOrdId)) {
            params.add("clOrdId=" + clOrdId);
        }

        String queryString = String.join("&", params);
        String response = get("/api/v5/trade/order", queryString);
        System.out.println("查询订单API响应: " + response); // 添加调试日志
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<Order> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null && !dataArray.isEmpty()) {
            JSONObject orderData = dataArray.getJSONObject(0);
            Order order = new Order();
            order.setInstId(orderData.getString("instId"));
            order.setOrdId(orderData.getString("ordId"));
            order.setClOrdId(orderData.getString("clOrdId"));
            order.setTag(orderData.getString("tag"));
            order.setSide(orderData.getString("side"));
            order.setPx(orderData.getString("px"));
            order.setSz(orderData.getString("sz"));
            order.setOrdType(orderData.getString("ordType"));
            order.setTdMode(orderData.getString("tdMode"));
            order.setState(orderData.getString("state"));
            order.setCTime(orderData.getString("cTime"));
            order.setUTime(orderData.getString("uTime"));
            order.setFillPx(orderData.getString("fillPx"));
            order.setFillSz(orderData.getString("fillSz"));
            order.setAccFillSz(orderData.getString("accFillSz"));
            order.setFee(orderData.getString("fee"));
            order.setFeeCcy(orderData.getString("feeCcy"));
            order.setRebate(orderData.getString("rebate"));
            order.setRebateCcy(orderData.getString("rebateCcy"));
            order.setPnl(orderData.getString("pnl"));
            order.setCategory(orderData.getString("category"));
            result.setData(order);
        }
        
        return result;
    }

    @Override
    public OkxResponse<List<Order>> getPendingOrders(String instType, String instId, String ordType, String state,
                                                   String after, String before, Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(instId)) {
            params.add("instId=" + instId);
        }
        if (StringUtils.isNotEmpty(ordType)) {
            params.add("ordType=" + ordType);
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
        String response = get("/api/v5/trade/orders-pending", queryString);
        JSONObject jsonObject = JSON.parseObject(response);
        
        OkxResponse<List<Order>> result = new OkxResponse<>();
        result.setCode(jsonObject.getString("code"));
        result.setMsg(jsonObject.getString("msg"));
        
        JSONArray dataArray = jsonObject.getJSONArray("data");
        if (dataArray != null) {
            List<Order> orders = new ArrayList<>();
            for (int i = 0; i < dataArray.size(); i++) {
                JSONObject orderData = dataArray.getJSONObject(i);
                if (!orderData.containsKey("sCode")) {
                    Order order = JSON.parseObject(orderData.toString(), Order.class);
                    orders.add(order);
                }
            }
            result.setData(orders);
        }
        
        return result;
    }

    @Override
    public OkxResponse<List<Order>> getOrderHistory(String instType, String instId, String ordType, String state,
                                                 String after, String before, Integer limit) throws IOException {
        List<String> params = new ArrayList<>();
        if (StringUtils.isNotEmpty(instType)) {
            params.add("instType=" + instType);
        }
        if (StringUtils.isNotEmpty(instId)) {
            params.add("instId=" + instId);
        }
        if (StringUtils.isNotEmpty(ordType)) {
            params.add("ordType=" + ordType);
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
        String response = get("/api/v5/trade/orders-history", queryString);
        return JSON.parseObject(response, new TypeReference<OkxResponse<List<Order>>>() {});
    }
} 