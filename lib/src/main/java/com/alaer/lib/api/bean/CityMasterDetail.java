package com.alaer.lib.api.bean;

public class CityMasterDetail {

    public int id;
    public String uuid;
    public int currencyId;
    public float diamond;
    public float diamondToday;
    public float diamondPrice;
    public float cityBonus;
    public float cityBonusToday;
    public float rewardBonus;
    public float rewardBonusToday;
    public float activityness;
    public float activitynessToday;
    public float game;
    public float gameToday;
    public float total;
    public float totalToday;
    public float offline;
    public float offlineToday;
    public float rate;
    public int level;
    public String createTime;
    public int isMaster;
    public String city;
    public String fullCity;
    public String startTime;
    public String endTime;
    public String name;
    public String avatar;
    public AreaDTO area;
    public int adcodeStatus;
    public int adcodeCommit;
    public String lon;
    public String lat;

    public static class AreaDTO {
        public City area;
        public City province;
        public City city;

        public static class City {
            public String adcode;
            public String name;
        }
    }

}
