package com.alaer.lib.api.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuangJW on 2021/11/6 15:00.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class AssetsTotalInfo {

    public float total;
    public List<Assets> assets;
    public float totalUsdt;
    public String baseCurrency;
    public String base;

    public static class Assets implements Serializable {
        public int currencyId;
        public int currencyType;
        public String currencyName;
        public String currencyNameEn;
        public String icoUrl;
        public int iconResId;
        public float point_num;
        public float point_price;
        public int status;
        public float price;
        public float priceUsdt;
        public float initPrice;
        public String actions;
        public String actionStatus;
        public float recharge;
        public float withdraw;
        public float trade;
        public float transfer;
        public float amount;
        public float cashAmount;
        public float freezeCashAmount;
        public float prepareSubAmount;
        public float freezeAmount;
        public float valuations;
        public float valuationsUsdt;
        public boolean selected;
    }

}