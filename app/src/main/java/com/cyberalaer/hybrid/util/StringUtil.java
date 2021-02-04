package com.cyberalaer.hybrid.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * To MD5
     */
    public static String toMD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 验证手机号有效性
     */
    public static boolean phoneIsValid(String phone) {
//        if (TextUtils.isEmpty(phone) || phone.length() != 11)
//            return false;

        String regex = "([0-9\\s-]{7,})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 验证密码有效性(8-16位数字或字符,不能包含特殊字符)
     */
    public static boolean pwdIsValid(String pwd) {
        if (TextUtils.isEmpty(pwd) || pwd.length() < 8 || pwd.length() > 16)
            return false;
        String regex = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z\\d\\S]{8,16}$";
        Matcher matcher = Pattern.compile(regex).matcher(pwd);
        return matcher.matches();
    }

    public static String getNullStringDefault(String text) {
        if (TextUtils.isEmpty(text) || TextUtils.equals(text, "null"))
            return "--";
        return text;
    }

}
