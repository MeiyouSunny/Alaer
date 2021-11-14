package com.alaer.lib.api.bean;

import java.util.List;

/**
 * Created by HuangJW on 2021/11/13 22:16.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class RechargeData {

    public int total;
    public float sumMoney;
    public List<RechargeRecord> points;

    public static class RechargeRecord {
        public String currencyNameEn;
        public String walletSn;
        public float realNum;
        public int fee;
        public String txId;
        public String icoUrl;
        public String rechargeId;
        public String currencyName;
        public String createTime;
        public float coinNum;
        public String confirms;
        public int currencyId;
        public int status;
        public int contractId;
    }
}