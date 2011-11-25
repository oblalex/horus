package ua.cn.stu.oop.horus.core.dao.hibernateImpl.object;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.TitleLinkCarrierQueries;
import ua.cn.stu.oop.horus.core.dao.object.GameObjectDao;
import ua.cn.stu.oop.horus.core.domain.object.GameObject;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;

/**
 *
 * @author alex
 */
@Repository("GameObjectDaoHibernateImpl")
public class GameObjectDaoHibernateImpl
        extends GenericDaoHibernateImpl<GameObject>
        implements GameObjectDao {

    public GameObjectDaoHibernateImpl() {
        super(GameObject.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public GameObject getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (GameObject) singleResultOrNullByQuery(query, titleLink);
    }
}
