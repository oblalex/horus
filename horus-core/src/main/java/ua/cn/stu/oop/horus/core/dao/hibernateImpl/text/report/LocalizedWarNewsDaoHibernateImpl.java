package ua.cn.stu.oop.horus.core.dao.hibernateImpl.text.report;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.text.report.LocalizedWarNewsDao;
import ua.cn.stu.oop.horus.core.domain.text.report.LocalizedWarNews;

/**
 *
 * @author alex
 */
@Repository("LocalizedWarNewsDaoHibernateImpl")
public class LocalizedWarNewsDaoHibernateImpl
        extends GenericDaoHibernateImpl<LocalizedWarNews>
        implements LocalizedWarNewsDao {

    public LocalizedWarNewsDaoHibernateImpl() {
        super(LocalizedWarNews.class);
    }
}
