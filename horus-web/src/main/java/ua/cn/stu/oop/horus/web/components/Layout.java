package ua.cn.stu.oop.horus.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.*;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;

/**
 *
 * @author alex
 */
@Import(library = {
    "context:js/digitalspaghetti.password.js",
    "context:js/jquery.sb.min.js",
    "context:js/radioCheckbox.js",
    "context:js/text-input.js",
    "context:js/cufon-yui.js",
    "context:js/timerBackward.js"},
stylesheet = {"context:css/common.css",
    "context:css/menu.css",
    "context:css/jquery.sb.css",
    "context:css/radioCheckbox.css",
    "context:css/text-input.css",
    "context:css/btn.css"})
public class Layout extends GenericPage {

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    public String getPageFullTitle() {

        return pageTitle + " - "
                + ConfigContainer.CONFIG.GENERAL.getProjectNameByLocale(getLocale());
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public String getPageTitle() {
        return pageTitle;
    }
}
