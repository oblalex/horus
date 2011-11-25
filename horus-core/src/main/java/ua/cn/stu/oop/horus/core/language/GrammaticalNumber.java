package ua.cn.stu.oop.horus.core.language;

/**
 *
 * @author alex
 */
public enum GrammaticalNumber {

    SINGULAR,
    PLURAL;
    
    public static GrammaticalNumber getDefault(){
        return SINGULAR;
    }
}
