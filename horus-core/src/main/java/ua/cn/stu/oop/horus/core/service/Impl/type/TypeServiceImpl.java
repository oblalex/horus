package ua.cn.stu.oop.horus.core.service.Impl.type;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.ParentCarrierDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.type.TypeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.type.*;
import ua.cn.stu.oop.horus.core.service.Impl.*;
import ua.cn.stu.oop.horus.core.service.Impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.type.TypeService;

/**
 *
 * @author alex
 */
@Service("typeService")
public class TypeServiceImpl
        extends GenericServiceImpl<Type, TypeDaoHibernateImpl>
        implements TypeService {

    @Autowired
    public TypeServiceImpl(TypeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public Type getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }

    @Override
    public Collection<Type> getEntitiesByType(EntityType type) {
        return (Collection<Type>) TypeCarrierServiceImpl.getEntitiesByTypeFromDao(type, dao);
    }

    @Override
    public Collection<Type> getChildrenOfParent(Type parent) {
        return (Collection<Type>) ParentCarrierServiceImpl.getChildrenOfParentFromDao(parent, (ParentCarrierDao) dao);
    }
}
