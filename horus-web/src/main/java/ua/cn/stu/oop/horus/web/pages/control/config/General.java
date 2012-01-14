package ua.cn.stu.oop.horus.web.pages.control.config;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.text.LocalizedTitle;
import ua.cn.stu.oop.horus.core.service.text.LocalizedDataService;
import ua.cn.stu.oop.horus.web.base.control.config.GenericConfigPage;
import ua.cn.stu.oop.horus.web.components.control.data.MainTitleSelector;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;
import ua.cn.stu.oop.horus.web.config.GeneralConfig;
import ua.cn.stu.oop.horus.web.util.LocaleUtil;

/**
 *
 * @author alex
 */
public class General extends GenericConfigPage {

    @Persist
    private GeneralConfig config;
    
    @Inject
    @Autowired
    private LocalizedDataService localizedDataService;
    
    @Inject
    private PersistentLocale persistentLocale;

    @Component
    private MainTitleSelector titleSelector;
    
    @Persist
    private LocalizedTitle projectTitle;    

    @Override
    public Object onSuccess() {
        titleSelector.unselectTitle();
        applyAndSaveConfig();
        return getFormZoneOrNull();
    }
    
    private void applyAndSaveConfig(){    
        config.projectNameTitleLinkId = projectTitle.getTitleLink().getId();
        this.config.cloneToObject(ConfigContainer.CONFIG.GENERAL);        
        ConfigContainer.save();
    }

    @Override
    public Object onActionFromCancelChanges() {
        titleSelector.unselectTitle();
        cloneConfig();
        resetProjectTitle();
        return getFormZoneOrNull();
    }
    
    private void cloneConfig(){
        ConfigContainer.CONFIG.GENERAL.cloneToObject(config);
    }

    @Override
    public Object onActionFromSetByDefault() {
        titleSelector.unselectTitle();
        config.reset();
        return getFormZoneOrNull();
    }
    
    public Object onActionFromDialogajaxlink() {
        titleSelector.setLtitle(projectTitle.getTitle());
        return titleSelector.getZoneOrNull();
    }
    
    public void beginRender(){   
        config = new GeneralConfig();
        cloneConfig();
        resetProjectTitle();        
    }
    
    private void resetProjectTitle(){
        projectTitle = config.getProjectTitleByLocale(
                    LocaleUtil.getLocaleFromPersistent(persistentLocale));
    }

    public String getProjectTitle() {        
        return getCurrentTitle().getTitle();
    }
    
    public String getProjectDescription() {        
        return localizedDataService.getByLocalizedTitle(getCurrentTitle()).getData();
    }
    
    private LocalizedTitle getCurrentTitle(){
        LocalizedTitle selectedTitle = titleSelector.getSelectedTitleOrNull();
        if (selectedTitle!=null){
            return selectedTitle;
        } else {
            return projectTitle;
        }
    }
}
