package ua.cn.stu.oop.horus.web.config;

import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
public class MailConfig implements Resetable, XCloneable<MailConfig> {

    public String username;
    public String password;
    public String host;
    public int port = 587;

    @Override
    public void reset() {
        username = WebConstants.getConstant("email.username");
        password = WebConstants.getConstant("email.password");
        host     = WebConstants.getConstant("email.host");
        port     = Integer.parseInt(WebConstants.getConstant("email.port"));
    }

    @Override
    public MailConfig clone() {
        MailConfig result = new MailConfig();
        cloneToObject(result);
        return result;
    }

    @Override
    public void cloneToObject(MailConfig o) {
        o.host=this.host;
        o.password=this.password;
        o.port=this.port;
        o.username=this.username;
    }
}
