package ua.cn.stu.oop.horus.core.util;

import java.sql.Timestamp;

/**
 *
 * @author alex
 */
public class DateTimeUtil {

    public static Timestamp getTimestampNow() {
        return new Timestamp(System.currentTimeMillis());
    }
}
