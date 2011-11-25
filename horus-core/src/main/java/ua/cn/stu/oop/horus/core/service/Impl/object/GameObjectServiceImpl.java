package ua.cn.stu.oop.horus.core.service.Impl.object;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.object.GameObjectDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.object.GameObject;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.object.GameObjectService;

/**
 *
 * @author alex
 */
@Service("gameObjectService")
public class GameObjectServiceImpl
        extends GenericServiceImpl<GameObject, GameObjectDaoHibernateImpl>
        implements GameObjectService {

    @Autowired
    public GameObjectServiceImpl(GameObjectDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public GameObject getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }
}
