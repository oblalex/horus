package ua.cn.stu.oop.horus.core.dao.servercommander.config;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.StringTitleCarrierDao;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;

/**
 *
 * @author alex
 */
public interface ConfigSchemeDao
        extends GenericDao<ConfigScheme>,
        StringTitleCarrierDao<ConfigScheme> {
}
