package ua.cn.stu.oop.horus.web.components.control.data;

import java.util.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PersistentLocale;
import org.apache.tapestry5.services.Request;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.text.LocalizedTitle;
import ua.cn.stu.oop.horus.core.language.AvailableLocale;
import ua.cn.stu.oop.horus.core.service.text.LocalizedTitleService;
import ua.cn.stu.oop.horus.web.util.LocaleUtil;

/**
 *
 * @author alex
 */
public class MainTitleSelector implements ClientElement{
    
    @Inject
    @Autowired
    private LocalizedTitleService localizedTitleService;
    
    @Parameter(name = "id", required=true, defaultPrefix=BindingConstants.LITERAL)
    private String idParameter;

    @Inject
    private Request request;
    
    @Inject
    private ComponentResources resources;
    
    @Inject
    private Messages messages;
    
    @Persist
    private LocalizedTitle selectedTitle;
    
    @Inject
    private PersistentLocale persistentLocale;
    
    private AvailableLocale locale = LocaleUtil.getLocaleFromPersistent(persistentLocale);
    
    @Persist
    private String ltitle;
    
    @Component
    private Form theForm;
    
    @Component
    private Zone zone;
    
    private Zone parentZone = null;
    
    public List<String> onProvideCompletionsFromLtitle(String string) {    
        return new ArrayList<String>(
                localizedTitleService.getMainTitlesForLocaleStartingWithString(
                    locale, string));
    }
    
    public void onValidate(){
        theForm.clearErrors();
        findTitle();
        writeErrorOnTitleNotFound();
    }
    
    private void findTitle(){
        selectedTitle = (LocalizedTitle) localizedTitleService.getEntityOrNullByTitle(ltitle);
    }
    
    private void writeErrorOnTitleNotFound() {
        if (selectedTitle == null) {
            theForm.recordError(messages.get("title.not.found"));
        }
    }

    public Object onSuccess(){
        return getParentZoneOrPage();
    }
    
    public Object onFailure(){
        return getZoneOrNull();
    }
    
    void onActionFromUnselect() {
        theForm.clearErrors();
        ltitle = null;
        selectedTitle = null;
    }
    
    public LocalizedTitle getSelectedTitleOrNull() {
        return selectedTitle;
    }  
    
    public Object getZoneOrNull() {
        return (request.isXHR()) ? getZone().getBody() : null;
    }
    
    public Zone getZone(){
        if (zone==null){
            zone = (Zone) resources.getEmbeddedComponent(getZoneId());
        }
        return zone;
    }

    @Override
    public String getClientId() {
        return idParameter;
    }
    
    public String getZoneId() {
        return getClientId()+"Zone";
    }

    public void setParentZone(Zone parentZone) {
        this.parentZone = parentZone;
    }
    
    private Object getParentZoneOrPage(){
        return (parentZone!=null)?parentZone:resources.getPage();
    }

    public String getLtitle() {
        return ltitle;
    }

    public void setLtitle(String ltitle) {
        this.ltitle = ltitle;
    }
}
