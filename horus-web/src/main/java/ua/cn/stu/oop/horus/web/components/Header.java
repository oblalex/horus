package ua.cn.stu.oop.horus.web.components;

import org.apache.tapestry5.annotations.Import;
import ua.cn.stu.oop.horus.web.base.GenericPage;
import ua.cn.stu.oop.horus.web.config.ConfigContainer;

/**
 *
 * @author alex
 */
@Import(library = "context:js/logoLoader.js")
public class Header extends GenericPage{

    public String getProjectName() {
        return ConfigContainer.CONFIG.GENERAL.getProjectNameByLocale(getLocale());
    }
    
    public String getProjectDescription() {
        return ConfigContainer.CONFIG.GENERAL.getProjectDecriptionByLocale(getLocale());
    }

    @Override
    public String getPageTitle() {
        return this.getClass().getSimpleName();
    }
}
