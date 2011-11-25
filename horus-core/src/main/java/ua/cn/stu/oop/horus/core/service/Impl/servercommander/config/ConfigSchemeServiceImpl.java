package ua.cn.stu.oop.horus.core.service.Impl.servercommander.config;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.servercommander.config.ConfigSchemeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.servercommander.config.ConfigSchemeService;

/**
 *
 * @author alex
 */
@Service("configSchemeService")
public class ConfigSchemeServiceImpl
        extends GenericServiceImpl<ConfigScheme, ConfigSchemeDaoHibernateImpl>
        implements ConfigSchemeService {

    @Autowired
    public ConfigSchemeServiceImpl(ConfigSchemeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<String> getAllTitles() {
        return dao.getAllTitles();
    }

    @Override
    public ConfigScheme getEntityOrNullByTitle(String title) {
        return dao.getEntityOrNullByTitle(title);
    }
}
