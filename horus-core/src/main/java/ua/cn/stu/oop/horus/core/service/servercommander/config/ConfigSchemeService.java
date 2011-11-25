package ua.cn.stu.oop.horus.core.service.servercommander.config;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.StringTitleCarrierService;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;

/**
 *
 * @author alex
 */
public interface ConfigSchemeService
        extends GenericService<ConfigScheme>,
        StringTitleCarrierService<ConfigScheme> {
}
