package ua.cn.stu.oop.horus.core.dao.servercommander.config;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigParameter;

/**
 *
 * @author alex
 */
public interface ConfigParameterDao
        extends GenericDao<ConfigParameter>,
        TitleLinkCarrierDao<ConfigParameter> {
}
