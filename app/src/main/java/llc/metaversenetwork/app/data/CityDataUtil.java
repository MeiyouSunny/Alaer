package llc.metaversenetwork.app.data;

import android.text.TextUtils;

import com.alaer.lib.api.bean.CityMasterDetail;
import llc.metaversenetwork.app.util.NumberUtils;

public class CityDataUtil {

    // 城市节点具体位置
    public String parseFullCity(CityMasterDetail cityMaster) {
        if (TextUtils.isEmpty(cityMaster.fullCity))
            return "";
        if (!cityMaster.fullCity.contains(","))
            return cityMaster.fullCity;
        String result = "";
        String[] datas = cityMaster.fullCity.split(",");
        for (String data : datas) {
            result = result + data;
        }

        return result;
    }

    // 有效期
    public String parseTermOfValidity(CityMasterDetail cityMaster) {
        String result = "";
        if (!TextUtils.isEmpty(cityMaster.startTime) && cityMaster.startTime.contains(" "))
            result = result + cityMaster.startTime.split(" ")[0];

        result = result + "~";

        if (!TextUtils.isEmpty(cityMaster.endTime) && cityMaster.endTime.contains(" "))
            result = result + cityMaster.endTime.split(" ")[0];

        return result;
    }

    public String parseNumber(float value) {
        if (value == 0)
            return "--";
        return NumberUtils.instance().parseNumber(value);
    }

}
