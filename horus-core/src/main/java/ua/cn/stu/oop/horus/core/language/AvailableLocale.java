package ua.cn.stu.oop.horus.core.language;

/**
 *
 * @author alex
 */
public enum AvailableLocale {
    en,
    ru;
    
    public static AvailableLocale getDefault(){
        return AvailableLocale.en;
    }
}
