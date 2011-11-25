package ua.cn.stu.oop.horus.core.dao.hibernateImpl.text;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.text.LocalizedDataDao;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
@Repository("LocalizedDataDaoHibernateImpl")
public class LocalizedDataDaoHibernateImpl
        extends GenericDaoHibernateImpl<LocalizedData>
        implements LocalizedDataDao {

    public LocalizedDataDaoHibernateImpl() {
        super(LocalizedData.class);
    }

    @Override
    public LocalizedData getByLocalizedTitle(LocalizedTitle localizedTitle) {
        return (LocalizedData)
                singleResultOrNullByNamedQuery(
                    "findByLocalizedTitleNativeSQL",
                    localizedTitle.getId());
    }
}
