package llc.metaversenetwork.app.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Time Util
 */
public class TimeUtil {

    public static final String FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_LOOP_FILE_NAME = "yyyy-MM-dd_HH-mm-ss";

    /**
     * 获取指定时间后30秒的时间字符串
     */
    public static String getBefore30sTimeString(Date date) {
        if (date == null)
            return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 30);
        return getNormalFormatTimeString(calendar.getTime());
    }

    public static String getNormalFormatTimeString(Date date) {
        return getNormalFormatTimeString(date, FORMAT_NORMAL);
    }

    public static String getNormalFormatTimeString(Date date, String format) {
        if (date == null)
            return "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.format(date);
    }

    public static long parseTimeToMillies(String dateString) {
        if (TextUtils.isEmpty(dateString))
            return 0;

        SimpleDateFormat formater = new SimpleDateFormat(FORMAT_NORMAL);
        try {
            return formater.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间字符串转毫秒值
     *
     * @param dateStr 时间字符串
     */
    public static long parseDateStrToMilles(String dateStr) {
        if (TextUtils.isEmpty(dateStr))
            return -1;

        try {
            SimpleDateFormat formater = new SimpleDateFormat(FORMAT_NORMAL);
            Date date = formater.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String parseMillesToTimeString(long time) {
        Date date = new Date(time);
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        formater.setTimeZone(TimeZone.getTimeZone("GMT+00"));
        return formater.format(date);
    }

    // 秒值 --> xx小时xx分钟
    public static String parseSecondssToTimeString(long time) {
        time *= 1000;
        Date date = new Date(time);
        String format = "H小时m分";
        if (time % (60 * 60) == 0)
            format = "H小时";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        formater.setTimeZone(TimeZone.getTimeZone("GMT+00"));
        return formater.format(date);
    }

    public static String parseMillesToTimeStringWithSecond(long time) {
        Date date = new Date(time);
        SimpleDateFormat formater = new SimpleDateFormat("MM-dd HH:mm:ss");
        return formater.format(date);
    }

    public static String parseMillesToShortTimeWithoutDay(long time) {
        Date date = new Date(time);
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        return formater.format(date);
    }

    public static String[] parseHourMinuteSecondFromMillis(long millis) {
        SimpleDateFormat formater = new SimpleDateFormat(FORMAT_NORMAL);
        Date date = new Date();
        millis = millis + 60 * 1000 * date.getTimezoneOffset();
        date.setTime(millis);
        String dateStr = formater.format(date);
        String[] times = dateStr.split(" ")[1].split(":");

        return times;
    }

    /**
     * 毫秒值 -> "xxxx-xx-xx"
     */
    public static String parseMillesToDateString(long time) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(time));
    }

    public static String parseToMinite(long timeStamp) {
        try {
            DateFormat sdf = new SimpleDateFormat("mm:ss");
            Date date = new Date();
            date.setTime(timeStamp);
            String result = sdf.format(date);
            return result;
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 获取当前时间字符串
     */
    public static String nowTimeString() {
        return getNormalFormatTimeString(new Date());
    }

    /**
     * 当年多少天
     *
     * @param year
     * @return
     */
    public static int getYearDays(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return 366;
        }
        return 365;
    }

}
