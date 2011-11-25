package ua.cn.stu.oop.horus.core.service.servercommander.config;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigParameterValue;

/**
 *
 * @author alex
 */
public interface ConfigParameterValueService
        extends GenericService<ConfigParameterValue>,
        ConfigSchemeCarrierService<ConfigParameterValue> {
}
