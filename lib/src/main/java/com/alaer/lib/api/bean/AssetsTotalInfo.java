package com.alaer.lib.api.bean;

import java.util.List;

/**
 * Created by HuangJW on 2021/11/6 15:00.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class AssetsTotalInfo {

    public Double total;
    public List<Assets> assets;
    public double totalUsdt;
    public String baseCurrency;
    public String base;

    public static class Assets {
        public int currencyId;
        public int currencyType;
        public String currencyName;
        public String currencyNameEn;
        public String icoUrl;
        public double point_num;
        public double point_price;
        public int status;
        public double price;
        public double priceUsdt;
        public double initPrice;
        public String actions;
        public String actionStatus;
        public double recharge;
        public double withdraw;
        public double trade;
        public double transfer;
        public double amount;
        public double cashAmount;
        public double freezeCashAmount;
        public double prepareSubAmount;
        public double freezeAmount;
        public double valuations;
        public double valuationsUsdt;
        public boolean selected;
    }

}