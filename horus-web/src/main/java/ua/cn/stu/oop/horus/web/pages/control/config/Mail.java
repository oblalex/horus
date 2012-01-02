package ua.cn.stu.oop.horus.web.pages.control.config;

import org.apache.tapestry5.annotations.*;
import ua.cn.stu.oop.horus.web.base.control.config.GenericConfigPage;
import ua.cn.stu.oop.horus.web.config.*;

/**
 *
 * @author alex
 */
public class Mail extends GenericConfigPage{

    @Persist
    @Property
    private MailConfig config;
    
    void beginRender(){
        config = new MailConfig();
        cloneConfig();
    }
    
    private void cloneConfig(){
        ConfigContainer.CONFIG.MAIL.cloneToObject(config);
    }
    
    @Override
    public Object onSuccess() {
        applyAndSaveConfig();
        return getZoneOrNull();
    }
    
    private void applyAndSaveConfig(){
        this.config.cloneToObject(ConfigContainer.CONFIG.MAIL);        
        ConfigContainer.save();
    }

    @Override
    public Object onActionFromCancelChanges() {
        cloneConfig();
        return getZoneOrNull();
    }

    @Override
    public Object onActionFromSetByDefault() {
        config.reset();
        return getZoneOrNull();
    }
}
