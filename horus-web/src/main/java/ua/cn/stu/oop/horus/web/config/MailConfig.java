package ua.cn.stu.oop.horus.web.config;

import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
public class MailConfig implements Resetable{

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
}
