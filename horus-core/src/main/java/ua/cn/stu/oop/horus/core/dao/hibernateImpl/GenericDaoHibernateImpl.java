package ua.cn.stu.oop.horus.core.dao.hibernateImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.CommonQueries;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
abstract public class GenericDaoHibernateImpl<E extends GenericDomain>
        extends HibernateDaoSupport
        implements GenericDao<E> {

    private Class<E> entityClass;

    protected Class<E> getEntityClass() {
        return entityClass;
    }

    public GenericDaoHibernateImpl(Class<E> entityType) {
        this.entityClass = entityType;
    }

    @Autowired
    public void foo(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public E entityOrNullById(Long id) {
        return (E) getHibernateTemplate().get(entityClass, id);
    }

    @Override
    public <E> Collection<E> allEntites() {
        String query = CommonQueries.getAllEntities(entityClass);
        List result = getHibernateTemplate().find(query);
        return (Collection<E>) originalOrEmptyIfNull(result);
    }

    @Override
    public Long saveAndGetId(E entity) {
        return (Long) getHibernateTemplate().save(entity);
    }

    @Override
    public void updateEntity(E entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void saveOrUpdateEntity(E entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void deleteEntity(E entity) {
        getHibernateTemplate().delete(entity);
    }

    protected Object singleResultOrNullByQuery(String query) {
        List result = getHibernateTemplate().find(query);
        return DataAccessUtils.singleResult(result);
    }
    
    protected Object singleResultOrNullByQuery(String query, Object ... values) {
        List result = getHibernateTemplate().find(query, values);
        return DataAccessUtils.singleResult(result);
    }
    
    protected Object singleResultOrNullByQueryWithNamedParam(String query, String paramName, Object value) {
        List result = getHibernateTemplate().findByNamedParam(query, paramName,value);
        return DataAccessUtils.singleResult(result);
    }
    
    protected Object singleResultOrNullByNamedQuery(String queryName, Object ... values) {        
        List response = getHibernateTemplate().findByNamedQuery(queryName, values);
        return DataAccessUtils.singleResult(response);
    }
    
    protected Object requiredSingleResultByQuery(String query) {
        List result = getHibernateTemplate().find(query);
        return DataAccessUtils.requiredSingleResult(result);
    }    
    
    protected Object requiredSingleResultByQuery(String query, Object ... values) {
        List result = getHibernateTemplate().find(query, values);
        return DataAccessUtils.requiredSingleResult(result);
    }
    
    protected Object requiredSingleResultByQueryWithNamedParam(String query, String paramName, Object value) {
        List result = getHibernateTemplate().findByNamedParam(query, paramName,value);
        return DataAccessUtils.requiredSingleResult(result);
    }
    
    protected Object requiredSingleResultByNamedQuery(String queryName, Object ... values) {        
        List response = getHibernateTemplate().findByNamedQuery(queryName, values);
        return DataAccessUtils.requiredSingleResult(response);
    }
    
    protected <T> Collection<T> multipleResultByQuery(String query) {
        List result = getHibernateTemplate().find(query);
        return (Collection<T>) originalOrEmptyIfNull(result);
    }    
    
    protected <T> Collection<T> multipleResultByQuery(String query, Object ... values) {
        List result = getHibernateTemplate().find(query, values);
        return (Collection<T>) originalOrEmptyIfNull(result);
    }
    
    protected <T> Collection<T> multipleResultByNamedQuery(String queryName, Object ... values) {
        List result = getHibernateTemplate().findByNamedQuery(queryName, values);
        return (Collection<T>) originalOrEmptyIfNull(result);
    }
    
    protected <T> Collection<T> multipleResultByQueryWithNamedParam(String query, String paramName, Object value) {
        List result = getHibernateTemplate().findByNamedParam(query, paramName,value);
        return (Collection<T>) originalOrEmptyIfNull(result);
    }
    
    protected <T> Collection<T> originalOrEmptyIfNull(Collection<T> c){
        return (c==null) ? Collections.EMPTY_LIST : c;
    }
}
