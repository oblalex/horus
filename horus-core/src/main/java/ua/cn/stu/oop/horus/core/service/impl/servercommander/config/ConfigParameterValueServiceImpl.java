package ua.cn.stu.oop.horus.core.service.impl.servercommander.config;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.servercommander.config.ConfigParameterValueDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.*;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.servercommander.config.ConfigParameterValueService;

/**
 *
 * @author alex
 */
@Service("configParameterValueService")
public class ConfigParameterValueServiceImpl
        extends GenericServiceImpl<ConfigParameterValue, ConfigParameterValueDaoHibernateImpl>
        implements ConfigParameterValueService {

    @Autowired
    public ConfigParameterValueServiceImpl(ConfigParameterValueDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<ConfigParameterValue> getEntitiesByConfigScheme(ConfigScheme configScheme) {
        return dao.getEntitiesByConfigScheme(configScheme);
    }
}
