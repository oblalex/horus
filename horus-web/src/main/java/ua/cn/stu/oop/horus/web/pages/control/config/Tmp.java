package ua.cn.stu.oop.horus.web.pages.control.config;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.web.base.control.config.GenericConfigPage;
import ua.cn.stu.oop.horus.web.config.*;
import ua.cn.stu.oop.horus.web.util.LocaleUtil;
import ua.cn.stu.oop.horus.web.util.pages.validator.DirectoryPathValidator;

/**
 *
 * @author alex
 */
public class Tmp  extends GenericConfigPage {

    @Inject
    private PersistentLocale persistentLocale;
    
    private AvailableLocale locale = LocaleUtil.getLocaleFromPersistent(persistentLocale);
    
    @Inject
    @Autowired
    private DirectoryPathValidator pathValidator;
    
    @Persist
    @Property
    private TmpConfig config;
    
    @Component
    private Form theForm;

    @Component
    private TextField dirPath; 
    
    void beginRender(){
        config = new TmpConfig();
        cloneConfig();
    }
    
    private void cloneConfig(){
        ConfigContainer.CONFIG.TMP.cloneToObject(config);
    }
        
    void onValidate(){
        theForm.clearErrors();
        theForm.recordError(
                dirPath,
                pathValidator.validateAndGetErrorMessageOrNull(
                    locale,
                    this.config.directoryPath));
    }
    
    public Object onFailure() {
        return getZoneOrNull();
    }
    
    @Override
    public Object onSuccess() {
        applyAndSaveConfig();
        return getZoneOrNull();
    }
    
    private void applyAndSaveConfig(){
        this.config.cloneToObject(ConfigContainer.CONFIG.TMP);        
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
