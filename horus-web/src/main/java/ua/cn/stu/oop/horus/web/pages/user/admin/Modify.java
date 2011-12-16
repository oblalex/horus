package ua.cn.stu.oop.horus.web.pages.user.admin;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.service.user.UserAdminService;
import ua.cn.stu.oop.horus.web.base.GenericPage;

/**
 *
 * @author alex
 */
@RequiresRoles(UserRoles.ADMIN)
public class Modify extends GenericPage {

    private String pageTitle;
    
    @Inject
    @Autowired
    private UserAdminService adminService;
    
    @Inject
    private Messages messages;
    
    @PageActivationContext
    @Property
    private Long itemId;
    
    @Property
    private UserAdmin item;

    public void onActivate(Long itemId) {
        item = (itemId==null) ? null : adminService.getEntityOrNullById(itemId);
    }

    @Override
    public String getPageTitle() {
        if (pageTitle==null){
            setPageTitle();
        }
        return pageTitle;
    }
    
    private void setPageTitle() {
        if (item == null) {
            setPageTitleOnAddingNewRecord();
        } else {
            setPageTitleOnEditing();
        }
    }

    private void setPageTitleOnAddingNewRecord() {
        pageTitle = messages.get("record.adding.new");
    }

    private void setPageTitleOnEditing() {
        pageTitle = messages.get("record.editing")+" - "+item.getUser().getLogin();
    }
}
