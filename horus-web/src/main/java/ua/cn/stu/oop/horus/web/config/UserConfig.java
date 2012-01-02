package ua.cn.stu.oop.horus.web.config;

import java.awt.Dimension;
import ua.cn.stu.oop.horus.web.util.WebConstants;
import ua.cn.stu.oop.horus.web.util.Resetable;
import ua.cn.stu.oop.horus.web.util.XCloneable;

/**
 *
 * @author alex
 */
public class UserConfig implements Resetable, XCloneable<UserConfig> {

    public boolean oneEmailPerUser;
    public final Dimension avatarDimensions = new Dimension();

    @Override
    public void reset() {
        oneEmailPerUser = Boolean.parseBoolean(WebConstants.getConstant("email.one.per.user"));
        resetAvatarDimensions();
    }

    private void resetAvatarDimensions() {
        avatarDimensions.height = Integer.parseInt(WebConstants.getConstant("user.avatar.height.px"));
        avatarDimensions.width = Integer.parseInt(WebConstants.getConstant("user.avatar.width.px"));
    }

    @Override
    public UserConfig clone(){
        UserConfig result = new UserConfig();
        result.oneEmailPerUser = this.oneEmailPerUser;
        result.avatarDimensions.height = this.avatarDimensions.height;
        result.avatarDimensions.width = this.avatarDimensions.width;
        return result;
    }
    
    @Override
    public void cloneToObject(UserConfig o){
        o.oneEmailPerUser = this.oneEmailPerUser;
        o.avatarDimensions.height = this.avatarDimensions.height;
        o.avatarDimensions.width = this.avatarDimensions.width;        
    }
}
