package llc.metaversenetwork.app.util;

import java.text.NumberFormat;

public class NumberUtils {
    private static NumberUtils ourInstance;

    public static NumberUtils instance() {
        if (ourInstance == null)
            ourInstance = new NumberUtils();
        return ourInstance;
    }

    private NumberUtils() {
    }

    public float parseFloat(float value) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
//        nf.setRoundingMode(RoundingMode.DOWN);
        return Float.valueOf(nf.format(value));
    }

    public String parseNumber(float value) {
        float newValue = parseFloat(value);
        int intValue = (int) newValue;
        if ((float) intValue == newValue)
            return String.valueOf(intValue) + ".00";

        String result = String.valueOf(newValue);
        if (result.contains(".")) {
            if (result.split("\\.")[1].length() == 1)
                return result + "0";
        }

        return String.valueOf(newValue);
    }

    public String parseNumber_(float value) {
        String result = String.valueOf(value);
        if (!result.contains("."))
            return result + ".00";
        int indexEnd = result.indexOf(".") + 3;
        if (indexEnd <= result.length())
            result = result.substring(0, indexEnd);
        return result;
    }

}
