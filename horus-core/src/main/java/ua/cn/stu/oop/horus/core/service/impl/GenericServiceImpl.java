package ua.cn.stu.oop.horus.core.service.impl;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.service.GenericService;

/**
 *
 * @author alex
 */
public abstract class GenericServiceImpl<E extends GenericDomain, D extends GenericDao<E>>
        implements GenericService<E> {
    
    protected final D dao;
    
    protected GenericServiceImpl(D dao) {
        this.dao = dao;
    }
    
    @Override
    public E getEntityOrNullById(Long id) {
        return this.dao.entityOrNullById(id);
    }
    
    @Override
    public Collection<E> getAllEntites() {
        return this.dao.allEntites();
    }
    
    @Override
    public Long saveAndGetId(E entity) {
        return this.dao.saveAndGetId(entity);
    }
    
    @Override
    public void updateEntity(E entity) {
        this.dao.updateEntity(entity);
    }
    
    @Override
    public void saveOrUpdateEntity(E entity) {
        this.dao.saveOrUpdateEntity(entity);
    }
    
    @Override
    public void deleteEntity(E entity) {
        this.dao.deleteEntity(entity);
    }
}
