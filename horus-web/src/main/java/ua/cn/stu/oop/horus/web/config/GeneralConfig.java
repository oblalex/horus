package ua.cn.stu.oop.horus.web.config;

import java.awt.Dimension;
import org.springframework.context.ApplicationContext;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.text.*;
import ua.cn.stu.oop.horus.web.util.*;

/**
 *
 * @author alex
 */
public class GeneralConfig implements Resetable{
            
    private static transient TitleLinkService titleLinkService;    
    private static transient LocalizedTitleService localizedTitleService;    
    private static transient LocalizedDataService localizedDataService;
    
    public Long projectNameTitleLinkId;
    public boolean oneEmailPerUser;
    public Dimension avatarDimensions = new Dimension();

    static {
        ApplicationContext ctx = ApplicationContextHelper.getContext();
        titleLinkService = ctx.getBean(TitleLinkService.class);
        localizedTitleService = ctx.getBean(LocalizedTitleService.class);
        localizedDataService = ctx.getBean(LocalizedDataService.class);
    }
    
    @Override
    public void reset() {
        oneEmailPerUser = Boolean.parseBoolean(Constants.getConstant("email.one.per.user"));
        avatarDimensions.height = Integer.parseInt(Constants.getConstant("user.avatar.height.px"));
        avatarDimensions.width = Integer.parseInt(Constants.getConstant("user.avatar.width.px"));
        resetProjectNameTitleLinkId();
    }
    
    private void resetProjectNameTitleLinkId(){
        if (projectNameTitleLinkId==null){
            createAndSaveNewProjectNameAndDescr();
        } else {
            resetExistingProjectNameAndDescr();
        }
    }
    
    private void createAndSaveNewProjectNameAndDescr(){
        TitleLink titleLink = new TitleLink();
        projectNameTitleLinkId = titleLinkService.saveAndGetId(titleLink);
        createAndSaveNewProjectNameAndDescrForTitleLinkAndLocale(titleLink, AvailableLocale.en);
        createAndSaveNewProjectNameAndDescrForTitleLinkAndLocale(titleLink, AvailableLocale.ru);
    }
    
    private void createAndSaveNewProjectNameAndDescrForTitleLinkAndLocale(TitleLink link, AvailableLocale locale){
        LocalizedTitle lt = new LocalizedTitle();        
        lt.setTitleLink(link);
        setDefaultTitle(lt, locale);
        localizedTitleService.saveAndGetId(lt);
        LocalizedData ld = new LocalizedData();
        setDefaultDescription(ld, lt);
        localizedDataService.saveAndGetId(ld);
    }
    
    private void resetExistingProjectNameAndDescr(){
        for(LocalizedTitle lt:localizedTitleService.getTitlesWithDefaultGrammarByTitleLinkId(projectNameTitleLinkId)){
            setDefaultTitle(lt, lt.getLocale());
            localizedTitleService.saveOrUpdateEntity(lt);
            LocalizedData ld = localizedDataService.getByLocalizedTitle(lt);
            setDefaultDescription(ld, lt);
            localizedDataService.saveOrUpdateEntity(ld);
        }
    }
    
    private void setDefaultTitle(LocalizedTitle title, AvailableLocale locale){
        title.setLocale(locale);
        title.setTitle(Messages.getMessage("default.project.name", locale));
    }
    
    private void setDefaultDescription(LocalizedData data, LocalizedTitle title){
        data.setLocalizedTitle(title);
        data.setData(Messages.getMessage("default.project.descr", title.getLocale()));
    }    
    
    public String getProjectNameByLocale(AvailableLocale locale){
        LocalizedTitle localizedTitle = 
                localizedTitleService.
                getTitleWithDefaultGrammarByTitleLinkIdAndLocale(projectNameTitleLinkId,locale);
        return localizedTitle.getTitle();
    }
    
    public String getProjectDecriptionByLocale(AvailableLocale locale){
        LocalizedTitle localizedTitle = 
                localizedTitleService.
                getTitleWithDefaultGrammarByTitleLinkIdAndLocale(projectNameTitleLinkId,locale);
        LocalizedData localizedData = localizedDataService.getByLocalizedTitle(localizedTitle);
        return localizedData.getData();
    }
}
