package ua.cn.stu.oop.horus.core.util;

import java.util.ResourceBundle;

/**
 *
 * @author alex
 */
public class CoreConstants {

    private static final String BUNDLE_NAME = "coreConstants";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);


    public static String getConstant(String key) {
        return bundle.getString(key);
    }

    public static String getConstantParameterized(String key, Object... params) {
        return String.format(bundle.getString(key), params);
    }
}
