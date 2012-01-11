package ua.cn.stu.oop.horus.core.dao.hibernateImpl.text.report;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.text.report.LocalizedChangesDao;
import ua.cn.stu.oop.horus.core.domain.text.report.LocalizedChanges;

/**
 *
 * @author alex
 */
@Repository("LocalizedChangesDaoHibernateImpl")
public class LocalizedChangesDaoHibernateImpl
        extends GenericDaoHibernateImpl<LocalizedChanges>
        implements LocalizedChangesDao {

    public LocalizedChangesDaoHibernateImpl() {
        super(LocalizedChanges.class);
    }

    @Override
    public Collection<String> getAllTitles() {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getAllTitles(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public LocalizedChanges getEntityOrNullByTitle(String title) {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getTitleCarriersByTitle(cls, title);
        return (LocalizedChanges)
                singleResultOrNullByQuery(query);
    }
}
