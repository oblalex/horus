package ua.cn.stu.oop.horus.core.dao.hibernateImpl.object;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.object.AircraftDao;
import ua.cn.stu.oop.horus.core.domain.object.Aircraft;

/**
 *
 * @author alex
 */
@Repository("AircraftDaoHibernateImpl")
public class AircraftDaoHibernateImpl
        extends GenericDaoHibernateImpl<Aircraft>
        implements AircraftDao {

    public AircraftDaoHibernateImpl() {
        super(Aircraft.class);
    }
}
