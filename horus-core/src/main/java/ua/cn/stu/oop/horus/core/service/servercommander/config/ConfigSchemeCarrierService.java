package ua.cn.stu.oop.horus.core.service.servercommander.config;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigSchemeCarrier;

/**
 *
 * @author alex
 */
public interface ConfigSchemeCarrierService<E extends ConfigSchemeCarrier> {

    public Collection<E> getEntitiesByConfigScheme(ConfigScheme configScheme);
}
