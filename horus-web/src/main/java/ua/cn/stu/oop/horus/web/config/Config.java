package ua.cn.stu.oop.horus.web.config;

import ua.cn.stu.oop.horus.web.util.Resetable;

/**
 *
 * @author alex
 */
public class Config implements Resetable{
    
    public final GeneralConfig GENERAL = new GeneralConfig();
    public final MailConfig MAIL = new MailConfig();
    public final UserConfig USER = new UserConfig();
    public final TmpConfig TMP = new TmpConfig();

    @Override
    public void reset() {
        GENERAL.reset();
        USER.reset();
        MAIL.reset();
        TMP.reset();
    }
}
