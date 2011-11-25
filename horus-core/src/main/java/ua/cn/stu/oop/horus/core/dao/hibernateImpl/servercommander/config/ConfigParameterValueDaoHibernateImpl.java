package ua.cn.stu.oop.horus.core.dao.hibernateImpl.servercommander.config;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.ConfigSchemeCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.servercommander.config.ConfigParameterValueDao;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.*;

/**
 *
 * @author alex
 */
@Repository("ConfigParameterValueDaoHibernateImpl")
public class ConfigParameterValueDaoHibernateImpl
        extends GenericDaoHibernateImpl<ConfigParameterValue>
        implements ConfigParameterValueDao {

    public ConfigParameterValueDaoHibernateImpl() {
        super(ConfigParameterValue.class);
    }

    @Override
    public Collection<ConfigParameterValue> getEntitiesByConfigScheme(ConfigScheme configScheme) {
        Class cls = getEntityClass();
        String query = ConfigSchemeCarrierQueries.
                getCarriersBySeries(cls);
        return multipleResultByQuery(query, configScheme);
    }
}
