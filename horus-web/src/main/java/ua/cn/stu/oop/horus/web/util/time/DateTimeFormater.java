package ua.cn.stu.oop.horus.web.util.time;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.util.Constants;
import ua.cn.stu.oop.horus.web.util.Messages;

/**
 *
 * @author alex
 */
public class DateTimeFormater {

    private final static String commonTimeFormat =
            Constants.getConstant("time.format.common");
    
    private final static String commonDateTimeFormat =
            Constants.getConstant("date.time.format.common");
    
    private final static String timeBackwardDateTimeFormat =
            Constants.getConstant("date.time.format.timeBackward");
    
    private final static String commonDateFormat =
            Constants.getConstant("date.format.common");
    
    private final static SimpleDateFormat cTimeF =
            new SimpleDateFormat(commonTimeFormat);
    
    private final static SimpleDateFormat cDateTimeF =
            new SimpleDateFormat(commonDateTimeFormat);
    
    private final static SimpleDateFormat tBWDateTimeF =
            new SimpleDateFormat(timeBackwardDateTimeFormat);
    
    private final static SimpleDateFormat sDateF =
            new SimpleDateFormat(commonDateFormat);

    public static String getCommonDateFormat() {
        return commonDateFormat;
    }

    public static String getCommonDateTimeFormat() {
        return commonDateTimeFormat;
    }

    public static String getTimeBackwardDateTimeFormat() {
        return timeBackwardDateTimeFormat;
    }

    public static String formateCommonTime(Time time) {
        return cTimeF.format(time);
    }

    public static String formateCommonDateTime(Timestamp datetime) {
        return cDateTimeF.format(datetime);
    }

    public static String formateTimeBackwardDateTime(Timestamp datetime) {
        return tBWDateTimeF.format(datetime);
    }

    public static String formateCommonDate(Date date) {
        return sDateF.format(date);
    }

    public static String formateCommonDate(Timestamp date) {
        return sDateF.format(date);
    }

    public static String formateTimeOmitZerosFull(long millis, AvailableLocale locale) {
        long timeInSec = millis / 1000;

        long hours = timeInSec / 3600;
        long t1 = timeInSec - hours * 3600;
        long minutes = (t1) / 60;
        long seconds = (t1 - minutes * 60);

        StringBuilder res = new StringBuilder();

        if (hours > 0) {
            res.append(hours).append(" ");
            res.append(
                    Messages.getMessage("metrics.hour", locale));
            res.append(
                    String.format(" %02d " + Messages.getMessage("metrics.minute", locale), minutes));
        } else if (minutes > 0) {
            res.append(minutes).append(" ").append(
                    Messages.getMessage("metrics.minute", locale));
        }

        if ((hours > 0) || (minutes > 0)) {
            res.append(
                    String.format(" %02d " + Messages.getMessage("metrics.second", locale), seconds));
        } else {
            res.append(seconds).append(" ").append(
                    Messages.getMessage("metrics.second", locale));
        }

        return res.toString();
    }

    public static String formateFlyTime(Timestamp date, AvailableLocale locale) {

        long timeInSec = date.getTime() / 1000;

        long hours = timeInSec / 3600;
        long minutes = (timeInSec - hours * 3600) / 60;

        String minutesStr = String.format(
                "%02d " + Messages.getMessage("metrics.minute", locale), minutes);

        if (hours > 0) {
            return hours + " " + Messages.getMessage("metrics.hour", locale) 
                         + " " + minutesStr;
        } else {
            return minutesStr;
        }
    }

    public static Timestamp parseCommonDate(String strDate) {
        try {
            return new Timestamp(sDateF.parse(strDate).getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
}
