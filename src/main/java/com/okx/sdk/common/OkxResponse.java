package com.okx.sdk.common;

import lombok.Data;

/**
 * OKX API 响应通用封装
 * @param <T> 响应数据类型
 */
@Data
public class OkxResponse<T> {
    /**
     * 响应码，0 代表成功
     */
    private String code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    public boolean isSuccessful() {
        return "0".equals(code) || "200".equals(code);
    }

    /**
     * 获取第一个数据项（如果data是数组）
     */
    @SuppressWarnings("unchecked")
    public T getFirstData() {
        if (data instanceof java.util.List && !((java.util.List<?>) data).isEmpty()) {
            return (T) ((java.util.List<?>) data).get(0);
        }
        return data;
    }
} 