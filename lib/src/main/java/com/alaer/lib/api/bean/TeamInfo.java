package com.alaer.lib.api.bean;

import java.io.Serializable;
import java.util.List;

public class TeamInfo implements Serializable {

    public Profile profile;
    public VirtualMiner virtualMiner;
    // 是否已经领取了试用树苗
    public boolean claimNewbieMiner;
    public boolean available;
    public String stime;
    public boolean validate;
    public int adcodeCommit;
    public int adcodeStatus;
    public int master;
    public int masterLocation;
    public int channel;
    public List<TodayMiners> todayMiners;
    public List<?> featureMiners;
    public List<?> expiredMiners;

    public static class Profile implements Serializable {
        /**
         * id : 700
         * uuid : be45337868b64049ada17ee4fb41da45
         * currencyId : 174
         * activityness : 583
         * minerActivityness : 583
         * promotionActivityness : 0
         * fireActivityness : 0
         * reputation : 2246.48961856
         * contribution : 56
         * level : 1
         * createTime : 2020-10-05 18:32:53
         * validateTime : 2020-10-05 18:32:52
         * validateFail : 0
         */

        public int id;
        public String uuid;
        public int currencyId;
        public float activityness;
        public float minerActivityness;
        public float promotionActivityness;
        public float fireActivityness;
        public double reputation;
        public int contribution;
        public int level;
        public String createTime;
        public String validateTime;
        public int validateFail;
    }

    public static class VirtualMiner implements Serializable {
        /**
         * id : null
         * minerId : null
         * currencyId : null
         * secondaryCurrencyId : null
         * uuid : null
         * type : null
         * price : null
         * num : null
         * minedNum : null
         * remainNum : null
         * createTime : null
         * name : null
         * duration : 12780
         * activeness : null
         * reputation : null
         * product : null
         * productivity : null
         * status : null
         * source : null
         * note : null
         * startTime : null
         * endTime : null
         * step : 2
         * stepStartTime : 2021-01-02 11:44:21
         * stepEndTime : 2021-01-02 12:14:21
         * todayProduct : 250.26582
         * todayProductivity : null
         * todayStatus : 1
         * todayStartTime : null
         * todayEndTime : null
         * todayRewardTime : 0
         * todayNum : 10
         */

        public Object id;
        public Object minerId;
        public Object currencyId;
        public Object secondaryCurrencyId;
        public Object uuid;
        public Object type;
        public Object price;
        public Object num;
        public Object minedNum;
        public Object remainNum;
        public Object createTime;
        public Object name;
        public int duration;
        public Object activeness;
        public Object reputation;
        public Object product;
        public Object productivity;
        public Object status;
        public Object source;
        public Object note;
        public String startTime;
        public String endTime;
        public int step;
        public String stepStartTime;
        public String stepEndTime;
        public double todayProduct;
        public Object todayProductivity;
        public int todayStatus;
        public Object todayStartTime;
        public Object todayEndTime;
        public int todayRewardTime;
        public int todayNum;
    }

    public static class TodayMiners implements Serializable {
        /**
         * id : 3288
         * minerId : 15
         * currencyId : 174
         * secondaryCurrencyId : 0
         * uuid : be45337868b64049ada17ee4fb41da45
         * type : 1
         * price : 10
         * num : 30
         * minedNum : 1
         * remainNum : 29
         * createTime : 2020-12-14 13:14:14
         * name : 枣树
         * duration : 10800
         * activeness : 1
         * reputation : 1
         * product : 11
         * productivity : 3.395E-5
         * status : 1
         * source : 0
         * note : null
         * startTime : 2020-12-15 00:00:00
         * endTime : 2021-01-13 23:59:59
         * step : null
         * stepStartTime : null
         * stepEndTime : null
         * todayProduct : 0.36666
         * todayProductivity : 3.395E-5
         * todayStatus : 1
         * todayStartTime : null
         * todayEndTime : null
         * todayRewardTime : null
         * todayNum : null
         */

        public int id;
        public int minerId;
        public int currencyId;
        public int secondaryCurrencyId;
        public String uuid;
        public int type;
        public int price;
        public int num;
        public int minedNum;
        public int remainNum;
        public String createTime;
        public String name;
        public int duration;
        public int activeness;
        public int reputation;
        public int product;
        public double productivity;
        public int status;
        public int source;
        public Object note;
        public String startTime;
        public String endTime;
        public Object step;
        public Object stepStartTime;
        public Object stepEndTime;
        public double todayProduct;
        public double todayProductivity;
        public int todayStatus;
        public Object todayStartTime;
        public Object todayEndTime;
        public Object todayRewardTime;
        public Object todayNum;
    }
}
