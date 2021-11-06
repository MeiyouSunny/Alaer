package llc.metaversenetwork.app.util;

import com.alaer.lib.api.bean.AssetsTotalInfo;

import java.util.ArrayList;
import java.util.List;

import llc.metaversenetwork.app.R;

/**
 * Created by HuangJW on 2021/11/6 18:26.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class WalletDataUtil {

    public static List<AssetsTotalInfo.Assets> parseAssetsList(List<AssetsTotalInfo.Assets> assets) {
        if (CollectionUtils.isEmpty(assets))
            return null;
        List<AssetsTotalInfo.Assets> result = new ArrayList<>();
        for (AssetsTotalInfo.Assets asset : assets) {
            if (asset.currencyId == 4 || asset.currencyId == 173) {
                if (asset.currencyId == 173) {
                    asset.currencyName = "MNC";
                    asset.currencyNameEn = "MNC";
                    asset.iconResId = R.drawable.ic_mnc;
                } else if (asset.currencyId == 4) {
                    asset.iconResId = R.drawable.ic_usdt;
                }
                result.add(asset);
            }
        }

        return result;
    }

}