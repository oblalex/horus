package ua.cn.stu.oop.horus.core.dao.hibernateImpl.servercommander.config;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleLinkCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.servercommander.config.ConfigParameterDao;
import ua.cn.stu.oop.horus.core.domain.servercommander.config.ConfigParameter;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;

/**
 *
 * @author alex
 */
@Repository("ConfigParameterDaoHibernateImpl")
public class ConfigParameterDaoHibernateImpl
        extends GenericDaoHibernateImpl<ConfigParameter>
        implements ConfigParameterDao {

    public ConfigParameterDaoHibernateImpl() {
        super(ConfigParameter.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public ConfigParameter getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (ConfigParameter) singleResultOrNullByQuery(query, titleLink);
    }
}
