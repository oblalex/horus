package ua.cn.stu.oop.horus.web.base.control.config;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import ua.cn.stu.oop.horus.core.domain.user.UserRoles;

/**
 *
 * @author alex
 */
@RequiresRoles(UserRoles.ADMIN)
public abstract class GenericConfigPage {

    @Inject
    private Request request;
    
    @Component(id="formZone")
    private Zone formZone; 
    
    protected Object getFormZoneOrNull(){
        return (request.isXHR()) ? getFormZone().getBody() : null;
    }

    public Zone getFormZone() {
        return formZone;
    }
    
    public abstract Object onSuccess();

    public abstract Object onActionFromCancelChanges();

    public abstract Object onActionFromSetByDefault();
}
