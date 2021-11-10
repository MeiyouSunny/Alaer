package llc.metaversenetwork.app.data;

import android.content.Context;

import com.alaer.lib.api.bean.ActiveBill;
import com.alaer.lib.api.bean.FruitBill;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.util.NumberUtils;

public class BillUtil {
    Context mContext;

    public BillUtil() {
    }

    public BillUtil(Context context) {
        mContext = context;
    }

    // 是否是树苗活跃度
    public boolean isSeedActivity(ActiveBill bill) {
        return bill != null && (bill.type == 1 || bill.type == 2 || bill.type == 5 || bill.type == 10);
    }

    // 1: 兑换 / 2: 到期 / 13: 统计 / 101: 分享
    public int parseTypeIcon(int billType) {
        if (billType == 1 || billType == 102 || billType == 103 || billType == 3)
            return R.drawable.ic_type_exchange;
        if (billType == 2 || billType == 9 || billType == 8 || billType == 104)
            return R.drawable.ic_type_expire;
        if (billType == 11 || billType == 12 || billType == 6)
            return R.drawable.ic_type_fire;
        if (billType == 13 || billType == 12 || billType == 14)
            return R.drawable.ic_type_statistics;
        if (billType == 101 || billType == 5 || billType == 10)
            return R.drawable.ic_type_share;

        return R.drawable.ic_type_statistics;
    }

    // 贡献值
    // 1.邀请好友；2.分享; 3.任务奖励
    public int parseContributionTypeIcon(int billType) {
        if (billType == 1 || billType == 2)
            return R.drawable.ic_type_share;
        if (billType == 3)
            return R.drawable.ic_type_gift;

        return R.drawable.ic_type_gift;
    }

    // WMNC
    public int parseWMNCTypeIcon(FruitBill bill) {
        if (bill.changeType == 430 || bill.changeType == 420)
            return R.drawable.ic_type_gift;
        if (bill.flag == 1)
            return R.drawable.ic_type_add;
        else
            return R.drawable.ic_type_reduce;
    }

    // 荣誉值
    // 1.矿机；100.推广；101.分享；102.任务；200.钻石；201.买入钻石;202.卖出钻石; 300充值
    public int parseHonorValueTypeIcon(int billType) {
        if (billType == 101)
            return R.drawable.ic_type_share;
        if (billType == 1 || billType == 102)
            return R.drawable.ic_type_gift;
        if (billType == 201 || billType == 300)
            return R.drawable.ic_type_recharge;
        if (billType == 202)
            return R.drawable.ic_type_reduce;

        return R.drawable.ic_type_gift;
    }

    // 活跃度
    // 1.矿机；100.推广；101.分享；102.任务；200.钻石；201.买入钻石;202.卖出钻石; 300充值
    public int parseActivityTypeIcon(int billType) {
        if (billType == 1 || billType == 102 || billType == 103 || billType == 3)
            return R.drawable.ic_type_exchange;
        if (billType == 2 || billType == 9 || billType == 8 || billType == 104)
            return R.drawable.ic_type_expire;
        if (billType == 11 || billType == 6)
            return R.drawable.ic_type_fire;
        if (billType == 13 || billType == 12)
            return R.drawable.ic_type_statistics;
        if (billType == 10 || billType == 5)
            return R.drawable.ic_type_gift;

        return R.drawable.ic_type_statistics;
    }

    public String parserActivtydesc(ActiveBill bill) {
        if (isSeedActivity(bill))
            return mContext.getString(R.string.sapling_activity_is, NumberUtils.instance().parseNumber(bill.refAmount));
        if (bill.type == 11 || bill.type == 6 || bill.type == 12)
            return mContext.getString(R.string.burn_active_is, NumberUtils.instance().parseNumber(bill.refAmount));
        else
            return mContext.getString(R.string.share_activity_is, NumberUtils.instance().parseNumber(bill.refAmount));

    }

}
