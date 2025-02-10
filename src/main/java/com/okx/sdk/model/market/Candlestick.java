package com.okx.sdk.model.market;

import com.alibaba.fastjson2.annotation.JSONCreator;
import lombok.Data;

/**
 * K线数据
 */
@Data
public class Candlestick {
    /**
     * 开始时间，Unix时间戳的毫秒数格式
     */
    private String ts;

    /**
     * 开盘价格
     */
    private String o;

    /**
     * 最高价格
     */
    private String h;

    /**
     * 最低价格
     */
    private String l;

    /**
     * 收盘价格
     */
    private String c;

    /**
     * 交易量，以币为单位
     */
    private String vol;

    /**
     * 交易量，以计价货币为单位
     */
    private String volCcy;

    /**
     * 交易量，以衍生品张数为单位（期权和衍生品情况下使用）
     */
    private String volCcyQuote;

    /**
     * 是否确定，true表示本K线期间已结束，false表示本K线期间未结束
     */
    private Boolean confirm;

    @JSONCreator
    public static Candlestick fromArray(String[] array) {
        if (array == null || array.length < 8) {
            throw new IllegalArgumentException("Invalid candlestick data array");
        }
        
        Candlestick candlestick = new Candlestick();
        candlestick.setTs(array[0]);
        candlestick.setO(array[1]);
        candlestick.setH(array[2]);
        candlestick.setL(array[3]);
        candlestick.setC(array[4]);
        candlestick.setVol(array[5]);
        candlestick.setVolCcy(array[6]);
        candlestick.setVolCcyQuote(array[7]);
        if (array.length > 8) {
            candlestick.setConfirm("1".equals(array[8]));
        }
        return candlestick;
    }
} 