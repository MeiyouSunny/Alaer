package com.cyberalaer.hybrid.data;

import com.alaer.lib.api.bean.ActiveBill;

public class BillUtil {

    // 是否是树苗活跃度
    public boolean isSeedActivity(ActiveBill bill) {
        return bill != null && (bill.type == 1 || bill.type == 2 || bill.type == 5 || bill.type == 10);
    }

}
