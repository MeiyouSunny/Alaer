package com.alaer.lib.api.bean;

public class Balance {

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

    public static class Diamond {
        /**
         * amount : 4238.90435136
         * cashAmount : 4238.90435136
         * currencyId : 174
         */

        public double amount;
        public double cashAmount;
        public int currencyId;
    }

    public static class Money {
        /**
         * amount : 0.25
         * cashAmount : 0.25
         */

        public double amount;
        public double cashAmount;
    }
}
