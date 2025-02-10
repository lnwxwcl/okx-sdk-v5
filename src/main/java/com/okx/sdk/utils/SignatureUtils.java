package com.okx.sdk.utils;

import com.okx.sdk.config.OkxConfig;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

/**
 * OKX API 签名工具类
 */
public class SignatureUtils {

    /**
     * 生成签名
     *
     * @param timestamp  时间戳
     * @param method    请求方法
     * @param requestPath 请求路径
     * @param body      请求体
     * @param secretKey 密钥
     * @return 签名
     */
    public static String sign(String timestamp, String method, String requestPath, String body, String secretKey) {
        if (StringUtils.isEmpty(body)) {
            body = "";
        }
        String preHash = timestamp + method.toUpperCase() + requestPath + body;
        return hmacSha256(preHash, secretKey);
    }

    /**
     * 获取ISO格式的时间戳
     */
    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    /**
     * HmacSHA256加密
     */
    private static String hmacSha256(String data, String secret) {
        try {
            Mac sha256Hmac = Mac.getInstance(OkxConfig.SIGN_METHOD);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), OkxConfig.SIGN_METHOD);
            sha256Hmac.init(secretKeySpec);
            byte[] hash = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign message", e);
        }
    }
} 