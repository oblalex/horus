package ua.cn.stu.oop.horus.core.service;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public interface GenericService <E extends GenericDomain> {
    
    public E getEntityOrNullById(Long id);
    public Collection<E> getAllEntites();
    public Long saveAndGetId(E entity);
    public void updateEntity(E entity);
    public void saveOrUpdateEntity(E entity);
    public void deleteEntity(E entity);
}
