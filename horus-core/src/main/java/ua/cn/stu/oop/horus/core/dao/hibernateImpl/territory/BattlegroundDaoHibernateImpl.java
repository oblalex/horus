package ua.cn.stu.oop.horus.core.dao.hibernateImpl.territory;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.territory.BattlegroundDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.territory.*;

/**
 *
 * @author alex
 */
@Repository("BattlegroundDaoHibernateImpl")
public class BattlegroundDaoHibernateImpl
        extends GenericDaoHibernateImpl<Battleground>
        implements BattlegroundDao {

    public BattlegroundDaoHibernateImpl() {
        super(Battleground.class);
    }

    @Override
    public Battleground getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (Battleground) singleResultOrNullByQuery(query, node);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public Battleground getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (Battleground) singleResultOrNullByQuery(query, titleLink);
    }   
}
