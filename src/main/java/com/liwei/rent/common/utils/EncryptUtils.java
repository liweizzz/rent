package com.liwei.rent.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class EncryptUtils {
    private static final Logger loger = LoggerFactory.getLogger(EncryptUtils.class);
    /**
     * AES加密
     * @param str
     * @param key
     * @return
     */
    public static String encrypt(String str, String key) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            loger.error("AES加密异常:{}", e.getMessage());
        }
        return null;
    }


    /**
     * AES 解密
     * @param str 被解密字符
     * @param key 解密key
     * @return 解密字符
     */
    public static String decrypt(String str, String key) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] raw = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(Base64.decodeBase64(str));
            return new String(encrypted);
        } catch (Exception e) {
            loger.error("AES解密异常:{}", e.getMessage());
        }
        return null;
    }
}
