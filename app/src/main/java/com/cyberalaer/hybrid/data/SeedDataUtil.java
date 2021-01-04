package com.cyberalaer.hybrid.data;

import android.content.res.Resources;

import com.cyberalaer.hybrid.R;

public class SeedDataUtil {
    // 0.试裁；1.枣树；100.葡萄树；200.苹果树；300.梨树；400.杏树

    public static final int TYPE_TRY = 0;
    public static final int TYPE_JUJUBE = 1;
    public static final int TYPE_GRAPE = 100;
    public static final int TYPE_APPLE = 200;
    public static final int TYPE_PEAR = 300;
    public static final int TYPE_GINKGO = 400;

    private Resources mRes;

    public SeedDataUtil(Resources mRes) {
        this.mRes = mRes;
    }

    public int getSeedName(int type) {
        int typeResId = R.string.seed_try;
        switch (type) {
            case TYPE_TRY:
                typeResId = R.string.seed_try;
                break;
            case TYPE_JUJUBE:
                typeResId = R.string.seed_jujube;
                break;
            case TYPE_GRAPE:
                typeResId = R.string.seed_grape;
                break;
            case TYPE_APPLE:
                typeResId = R.string.seed_apple;
                break;
            case TYPE_PEAR:
                typeResId = R.string.seed_pear;
                break;
            case TYPE_GINKGO:
                typeResId = R.string.seed_ginkgo;
                break;
        }

        return typeResId;
    }

    public int getSeedImage(int type) {
        int typeResId = R.drawable.img_seed_try;
        switch (type) {
            case TYPE_TRY:
                typeResId = R.drawable.img_seed_try;
                break;
            case TYPE_JUJUBE:
                typeResId = R.drawable.img_seed_jujube;
                break;
            case TYPE_GRAPE:
                typeResId = R.drawable.img_seed_grape;
                break;
            case TYPE_APPLE:
                typeResId = R.drawable.img_seed_apple;
                break;
            case TYPE_PEAR:
                typeResId = R.drawable.img_seed_pear;
                break;
            case TYPE_GINKGO:
                typeResId = R.drawable.img_seed_ginkgo;
                break;
        }

        return typeResId;
    }

    public String parseGrowDays(int days) {
        return mRes.getString(R.string.grow_days_is, days);
    }

    public String parseYield(int yield) {
        return mRes.getString(R.string.yield_is, yield);
    }

    public String parseStartTime(String time) {
        if (time.contains(" "))
            time = time.split(" ")[0];
        return mRes.getString(R.string.start_time_is, time);
    }

    public String parseEndTime(String time) {
        if (time.contains(" "))
            time = time.split(" ")[0];
        return mRes.getString(R.string.end_time_is, time);
    }

    public String parseRemainDays(int days) {
        return mRes.getString(R.string.remaing_days_is, days);
    }

    /**
     * 是否是星级市民赠送
     *
     * @param source 5:true
     * @return
     */
    public boolean isStartLevelReward(int source) {
        return source == 5;
    }

    public String parseRewardActivity(int num) {
        return mRes.getString(R.string.reward_activity_is, num);
    }

    public String parseCultivationTime(int num) {
        return mRes.getString(R.string.cultivation_time_is, num);
    }

    public String parseCultivationDays(int num) {
        return mRes.getString(R.string.cultivation_days_is, num);
    }

    public String parseHasExchanged(int exchanged, int total) {
        return mRes.getString(R.string.has_exchanged_is, exchanged, total);
    }

    public String parsePrice(int price) {
        if (price == 0)
            return mRes.getString(R.string.price_is_get_by_auth);
        return mRes.getString(R.string.price_is, price);
    }

}
