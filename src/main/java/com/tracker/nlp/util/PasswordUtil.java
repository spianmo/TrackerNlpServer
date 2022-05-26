package com.tracker.nlp.util;/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: PasswordUtil.java
 * @LastModified: 2022/03/14 22:03:14
 */

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtil {

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for (int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }

            return result;
        }
    }

    /**
     * 解密方法
     *
     * @param rawData 要解密的数据
     * @param key     解密key
     * @param iv      解密iv
     * @return 解密的结果
     */
    public static String desEncrypt(String rawData, String key, String iv) {
        if (iv.length() < 16) {
            int requireZeroSize = 16 - iv.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < requireZeroSize; i++) {
                sb.append("0");
            }
            iv += sb.toString();
            key += sb.toString();
        }
        try {
            byte[] encryp = parseHexStr2Byte(rawData);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] original = cipher.doFinal(encryp);
            return new String(original).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

