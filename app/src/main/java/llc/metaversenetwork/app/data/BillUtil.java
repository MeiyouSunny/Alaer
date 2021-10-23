package llc.metaversenetwork.app.data;

import com.alaer.lib.api.bean.ActiveBill;
import llc.metaversenetwork.app.R;

public class BillUtil {

    // 是否是树苗活跃度
    public boolean isSeedActivity(ActiveBill bill) {
        return bill != null && (bill.type == 1 || bill.type == 2 || bill.type == 5 || bill.type == 10);
    }

    // 1: 兑换 / 2: 到期 / 13: 统计 / 101: 分享
    public int parseTypeIcon(int billType) {
        if (billType == 1)
            return R.drawable.ic_type_exchange;
        if (billType == 2)
            return R.drawable.ic_type_expire;
        if (billType == 13)
            return R.drawable.ic_type_statistics;
        if (billType == 101)
            return R.drawable.ic_type_share;

        return R.drawable.ic_type_statistics;
    }

}
