package com.alaer.lib.util;

import android.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class StringUtil {

    public static String HMACSHA256(String data, String key) {
        try {
            Mac mac = Mac.getInstance("HMACSHA256");
            byte[] secretByte = key.getBytes("UTF-8");
            byte[] dataByte = data.getBytes("UTF-8");
            SecretKeySpec secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);
            byte[] array = mac.doFinal(dataByte);
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String base64(String content) {
        try {
            return Base64.encodeToString(content.getBytes(), Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
