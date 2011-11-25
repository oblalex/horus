package ua.cn.stu.oop.horus.core.service.servercommander;

import ua.cn.stu.oop.horus.core.domain.servercommander.ServerCommander;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.servercommander.config.ConfigSchemeCarrierService;

/**
 *
 * @author alex
 */
public interface ServerCommanderService
        extends GenericService<ServerCommander>,
        ConfigSchemeCarrierService<ServerCommander> {
}
