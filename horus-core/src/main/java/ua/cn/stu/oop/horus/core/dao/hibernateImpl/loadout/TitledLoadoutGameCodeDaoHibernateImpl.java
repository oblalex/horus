package ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleLinkCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.loadout.TitledLoadoutGameCodeDao;
import ua.cn.stu.oop.horus.core.domain.loadout.TitledLoadoutGameCode;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;

/**
 *
 * @author alex
 */
@Repository("TitledLoadoutGameCodeDaoHibernateImpl")
public class TitledLoadoutGameCodeDaoHibernateImpl
        extends GenericDaoHibernateImpl<TitledLoadoutGameCode>
        implements TitledLoadoutGameCodeDao {

    public TitledLoadoutGameCodeDaoHibernateImpl() {
        super(TitledLoadoutGameCode.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public TitledLoadoutGameCode getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (TitledLoadoutGameCode)
                singleResultOrNullByQuery(query, titleLink);
    }
    
}
