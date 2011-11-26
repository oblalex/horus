package ua.cn.stu.oop.horus.core.service.impl.servercommander;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.servercommander.ServerCommanderDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.servercommander.ServerCommander;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.servercommander.ServerCommanderService;

/**
 *
 * @author alex
 */
@Service("serverCommanderService")
public class ServerCommanderServiceImpl
        extends GenericServiceImpl<ServerCommander, ServerCommanderDaoHibernateImpl>
        implements ServerCommanderService {

    @Autowired
    public ServerCommanderServiceImpl(ServerCommanderDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<ServerCommander> getEntitiesByConfigScheme(ConfigScheme configScheme) {
        return dao.getEntitiesByConfigScheme(configScheme);
    }
}
