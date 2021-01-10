package com.alaer.lib.api.bean;

import java.io.Serializable;

public class Balance implements Serializable {

    /**
     * identityFee : 0.01
     * diamond : {"amount":4238.90435136,"cashAmount":4238.90435136,"currencyId":174}
     * money : {"amount":0.25,"cashAmount":0.25}
     * level : 1
     * withdrawAmountLimit : 500
     * feeRate : 45
     */

    public double identityFee;
    public Diamond diamond;
    public Money money;
    public int level;
    public int withdrawAmountLimit;
    public int feeRate;

    public static class Diamond implements Serializable {
        /**
         * amount : 4238.90435136
         * cashAmount : 4238.90435136
         * currencyId : 174
         */

        public float amount;
        public float cashAmount;
        public int currencyId;
    }

    public static class Money implements Serializable {
        /**
         * amount : 0.25
         * cashAmount : 0.25
         */

        public float amount;
        public float cashAmount;
    }
}
