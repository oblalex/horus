package ua.cn.stu.oop.horus.core.util.time;

/**
 *
 * @author alex
 */
public class TimeZoneUtil {
    
    public final static short TIMEZONE_DEFAULT = 0; 
    
    private final static short[] TIMEZONES = new short[26];
    private final static String[] TIMEZONES_STR = new String[TIMEZONES.length];
    
    static {
        for (short i = 0; i < TIMEZONES_STR.length; i++) {
            short tz = (short)(i - 12);
            TIMEZONES[i] = tz;
            TIMEZONES_STR[i] = toString(tz);
        }
    }

    public static short[] getTimeZones() {
        return TIMEZONES;
    }

    public static String[] getTimeZonesStrings() {
        return TIMEZONES_STR;
    }
   
    public static String toString(short timeZone){
        return String.format("%+d", timeZone);
    }
    
    public static short parseTimeZone(String timeZone){
        return Short.parseShort(timeZone.replace("+", ""));
    }
}
