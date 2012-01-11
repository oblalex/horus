package ua.cn.stu.oop.horus.core.dao.hibernateImpl.servercommander.config;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.servercommander.config.ConfigSchemeDao;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigScheme;

/**
 *
 * @author alex
 */
@Repository("ConfigSchemeDaoHibernateImpl")
public class ConfigSchemeDaoHibernateImpl
        extends GenericDaoHibernateImpl<ConfigScheme>
        implements ConfigSchemeDao {

    public ConfigSchemeDaoHibernateImpl() {
        super(ConfigScheme.class);
    }

    @Override
    public Collection<String> getAllTitles() {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getAllTitles(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public ConfigScheme getEntityOrNullByTitle(String title) {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getTitleCarriersByTitle(cls, title);
        return (ConfigScheme) singleResultOrNullByQuery(query);
    }
}
