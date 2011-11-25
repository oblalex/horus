package ua.cn.stu.oop.horus.core.service.Impl.servercommander.config;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.servercommander.config.ConfigParameterDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigParameter;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.servercommander.config.ConfigParameterService;

/**
 *
 * @author alex
 */
@Service("configParameterService")
public class ConfigParameterServiceImpl
        extends GenericServiceImpl<ConfigParameter, ConfigParameterDaoHibernateImpl>
        implements ConfigParameterService {

    @Autowired
    public ConfigParameterServiceImpl(ConfigParameterDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return dao.getAllTitleLinks();
    }

    @Override
    public ConfigParameter getEntityOrNullByTitleLink(TitleLink titleLink) {
        return dao.getEntityOrNullByTitleLink(titleLink);
    }
}
