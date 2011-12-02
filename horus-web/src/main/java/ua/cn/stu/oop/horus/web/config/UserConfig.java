package ua.cn.stu.oop.horus.web.config;

import java.awt.Dimension;
import ua.cn.stu.oop.horus.web.util.Constants;
import ua.cn.stu.oop.horus.web.util.Resetable;

/**
 *
 * @author alex
 */
public class UserConfig implements Resetable{

    public boolean oneEmailPerUser;
    public final Dimension avatarDimensions = new Dimension();
    
    @Override
    public void reset() {
        oneEmailPerUser = Boolean.parseBoolean(Constants.getConstant("email.one.per.user"));
        resetAvatarDimensions();
    }
    
    private void resetAvatarDimensions(){
        avatarDimensions.height = Integer.parseInt(Constants.getConstant("user.avatar.height.px"));
        avatarDimensions.width = Integer.parseInt(Constants.getConstant("user.avatar.width.px"));
    }
}
