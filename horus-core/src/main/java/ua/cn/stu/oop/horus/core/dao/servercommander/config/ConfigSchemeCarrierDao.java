package ua.cn.stu.oop.horus.core.dao.servercommander.config;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigSchemeCarrier;

/**
 *
 * @author alex
 */
public interface ConfigSchemeCarrierDao<E extends ConfigSchemeCarrier> {

    public Collection<E> getEntitiesByConfigScheme(ConfigScheme configScheme);
}
