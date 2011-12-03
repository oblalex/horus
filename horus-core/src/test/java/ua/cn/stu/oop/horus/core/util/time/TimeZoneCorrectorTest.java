package ua.cn.stu.oop.horus.core.util.time;

import java.sql.Timestamp;
import org.junit.Test;

/**
 *
 * @author alex
 */
public class TimeZoneCorrectorTest {
    
    @Test
    public void testCorrectTimeZone() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp nowPlusZone2 = TimeZoneCorrector.correctTimeZone(now, (short)0, (short)2);
        Timestamp nowMinusZone2 = TimeZoneCorrector.correctTimeZone(now, (short)2, (short)0);
        System.out.println(now);
        System.out.println(nowPlusZone2);
        System.out.println(nowMinusZone2);
    }
}
