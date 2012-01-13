package ua.cn.stu.oop.horus.web.components.control.config;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;

/**
 *
 * @author alex
 */
public class ConfigLayout {

    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String pageTitle;

    public String getPageTitle() {
        return pageTitle;
    }
}
