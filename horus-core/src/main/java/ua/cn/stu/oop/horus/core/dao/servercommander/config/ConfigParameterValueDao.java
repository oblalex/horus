package ua.cn.stu.oop.horus.core.dao.servercommander.config;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigParameterValue;

/**
 *
 * @author alex
 */
public interface ConfigParameterValueDao
        extends GenericDao<ConfigParameterValue>,
        ConfigSchemeCarrierDao<ConfigParameterValue> {
}
