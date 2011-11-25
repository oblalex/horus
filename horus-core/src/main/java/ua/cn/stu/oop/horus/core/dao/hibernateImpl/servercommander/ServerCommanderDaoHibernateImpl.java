package ua.cn.stu.oop.horus.core.dao.hibernateImpl.servercommander;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.ConfigSchemeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.servercommander.ServerCommanderDao;
import ua.cn.stu.oop.horus.core.domain.servercommander.ServerCommander;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;

/**
 *
 * @author alex
 */
@Repository("ServerCommanderDaoHibernateImpl")
public class ServerCommanderDaoHibernateImpl
        extends GenericDaoHibernateImpl<ServerCommander>
        implements ServerCommanderDao {

    public ServerCommanderDaoHibernateImpl() {
        super(ServerCommander.class);
    }

    @Override
    public Collection<ServerCommander> getEntitiesByConfigScheme(ConfigScheme configScheme) {
        Class cls = getEntityClass();
        String query = ConfigSchemeCarrierQueries.
                getCarriersBySeries(cls);
        return multipleResultByQuery(query, configScheme);
    }
}
