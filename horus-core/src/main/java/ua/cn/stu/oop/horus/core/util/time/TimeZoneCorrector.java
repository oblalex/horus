package ua.cn.stu.oop.horus.core.util.time;

import java.sql.Timestamp;

/**
 *
 * @author alex
 */
public class TimeZoneCorrector {
    
    public static Timestamp correctTimeZone(
            Timestamp srcTimestamp, short srcTimezone, short dstTimezone){
        
        long timeMillis = srcTimestamp.getTime();
        timeMillis = timeMillis + ((long)(dstTimezone-srcTimezone)*3600000);
        
        return new Timestamp(timeMillis);
    }
}
