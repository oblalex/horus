package ua.cn.stu.oop.horus.core.dao.servercommander;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.servercommander.config.ConfigSchemeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.servercommander.ServerCommander;

/**
 *
 * @author alex
 */
public interface ServerCommanderDao
        extends GenericDao<ServerCommander>,
        ConfigSchemeCarrierDao<ServerCommander>{
}
