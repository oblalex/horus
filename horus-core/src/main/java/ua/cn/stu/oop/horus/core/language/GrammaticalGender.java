package ua.cn.stu.oop.horus.core.language;

/**
 *
 * @author alex
 */
public enum GrammaticalGender {

    MALE,
    FEMALE,
    NEUTER;
    
    public static GrammaticalGender getDefault(){
        return MALE;
    }
}
