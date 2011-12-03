package ua.cn.stu.oop.horus.web.config;

import java.awt.Dimension;
import ua.cn.stu.oop.horus.web.util.WebConstants;
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
        oneEmailPerUser = Boolean.parseBoolean(WebConstants.getConstant("email.one.per.user"));
        resetAvatarDimensions();
    }
    
    private void resetAvatarDimensions(){
        avatarDimensions.height = Integer.parseInt(WebConstants.getConstant("user.avatar.height.px"));
        avatarDimensions.width = Integer.parseInt(WebConstants.getConstant("user.avatar.width.px"));
    }
}
