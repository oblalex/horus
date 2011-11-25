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
        username = Constants.getConstant("email.username");
        password = Constants.getConstant("email.password");
        host     = Constants.getConstant("email.host");
        port     = Integer.parseInt(Constants.getConstant("email.port"));
    }
}
