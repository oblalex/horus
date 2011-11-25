package ua.cn.stu.oop.horus.web.config;

import ua.cn.stu.oop.horus.web.util.Resetable;

/**
 *
 * @author alex
 */
public class Config implements Resetable{
    
    public GeneralConfig GENERAL = new GeneralConfig();
    public MailConfig MAIL = new MailConfig();
    public TmpConfig TMP = new TmpConfig();

    @Override
    public void reset() {
        GENERAL.reset();
        MAIL.reset();
        TMP.reset();
    }
}
