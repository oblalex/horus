package ua.cn.stu.oop.horus.web.util;

/**
 *
 * @author alex
 */
public class RegExpUtil {

    public static final String LOGIN_TAMPLATE = "^[\\w-=+_/.\\(\\)\\[\\]\\{\\}@]{3,30}$";
    public static final String EMAIL_TAMPLATE = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
}
