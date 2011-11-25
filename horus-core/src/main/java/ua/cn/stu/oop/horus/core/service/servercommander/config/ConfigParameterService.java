package ua.cn.stu.oop.horus.core.service.servercommander.config;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigParameter;

/**
 *
 * @author alex
 */
public interface ConfigParameterService
        extends GenericService<ConfigParameter>,
        TitleLinkCarrierService<ConfigParameter> {
}
