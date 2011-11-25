package ua.cn.stu.oop.horus.core.language;

/**
 *
 * @author alex
 */
public enum GrammaticalCase {

    NOMINATIVE,
    ACCUSATIVE,
    SOCIAL,
    DATIVE,
    MONION,
    GENITIVE,
    LOCATIVE,
    VOCATIVE;
    
    public static GrammaticalCase getDefault(){
        return NOMINATIVE;
    }
}
