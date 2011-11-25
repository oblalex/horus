package ua.cn.stu.oop.horus.core.dao.hibernateImpl.type;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.type.TypeDao;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.domain.type.*;

/**
 *
 * @author alex
 */
@Repository("TypeDaoHibernateImpl")
public class TypeDaoHibernateImpl
        extends GenericDaoHibernateImpl<Type>
        implements TypeDao {

    public TypeDaoHibernateImpl() {
        super(Type.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public Type getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (Type) singleResultOrNullByQuery(query, titleLink);
    }

    @Override
    public Collection<Type> getEntitiesByType(EntityType type) {
        Class cls = getEntityClass();
        String query = TypeCarrierQueries.
                getCarriersByType(cls);
        return multipleResultByQuery(query, type);
    }

    @Override
    public Collection<Type> getChildrenOfParent(Type parent) {
        Class cls = getEntityClass();
        String query = ParentCarrierQueries.
                getChildrenByParent(cls);
        String paramTitle = ParentCarrierQueries.
                PARAMETER_PARENT;
        return multipleResultByQueryWithNamedParam(query, paramTitle, parent);
    }    
}
