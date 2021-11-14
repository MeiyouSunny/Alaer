package com.alaer.lib.api.bean;

import java.util.List;

/**
 * Created by HuangJW on 2021/11/13 22:16.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class WithdrawalData {

    public int total;
    public List<WithdrawalRecord> list;

    public static class WithdrawalRecord {
        public String currencyNameEn;
        public String walletSn;
        public float realNum;
        public float fee;
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