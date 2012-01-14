package ua.cn.stu.oop.horus.web.pages.control.config;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import ua.cn.stu.oop.horus.web.base.control.config.GenericConfigPage;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.config.UserConfig;

/**
 *
 * @author alex
 */
public class Users extends GenericConfigPage {
    
    @Persist
    @Property
    private UserConfig config;

    void beginRender(){
        config = new UserConfig();
        cloneConfig();
    }
    
    private void cloneConfig(){
        ConfigContainer.CONFIG.USER.cloneToObject(config);
    }
    
    @Override
    public Object onSuccess() {
        applyAndSaveConfig();
        return getFormZoneOrNull();
    }
    
    private void applyAndSaveConfig(){
        this.config.cloneToObject(ConfigContainer.CONFIG.USER);        
        ConfigContainer.save();
    }

    @Override
    public Object onActionFromCancelChanges() {
        cloneConfig();
        return getFormZoneOrNull();
    }

    @Override
    public Object onActionFromSetByDefault() {        
        config.reset();
        return getFormZoneOrNull();
    }
    
    public int getAvatarWidth(){
        return config.avatarDimensions.width;
    }
    
    public void setAvatarWidth(int val){
        this.config.avatarDimensions.width=val;
    }
    
    public int getAvatarHeight(){
        return config.avatarDimensions.height;
    }
    
    public void setAvatarHeight(int val){
        this.config.avatarDimensions.height=val;
    }
}
