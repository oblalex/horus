package ua.cn.stu.oop.horus.core.dao;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public interface GenericDao<E extends GenericDomain> {

    public E entityOrNullById(Long id);

    public <E> Collection<E> allEntites();

    public Long saveAndGetId(E entity);

    public void updateEntity(E entity);

    public void saveOrUpdateEntity(E entity);

    public void deleteEntity(E entity);

    public void deleteEntityById(Long id);
}
