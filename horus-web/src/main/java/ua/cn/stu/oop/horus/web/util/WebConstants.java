package ua.cn.stu.oop.horus.web.util;

import java.util.ResourceBundle;

/**
 *
 * @author alex
 */
public class WebConstants {

    private static final String BUNDLE_NAME = "constants";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);


    public static String getConstant(String key) {
        return bundle.getString(key);
    }

    public static String getConstantParameterized(String key, Object... params) {
        return String.format(bundle.getString(key), params);
    }
}
