package ua.cn.stu.oop.horus.core.service.Impl.object;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.object.AircraftDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.object.Aircraft;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.object.AircraftService;

/**
 *
 * @author alex
 */
@Service("aircraftService")
public class AircraftServiceImpl
        extends GenericServiceImpl<Aircraft, AircraftDaoHibernateImpl>
        implements AircraftService {

    @Autowired
    public AircraftServiceImpl(AircraftDaoHibernateImpl dao) {
        super(dao);
    }
}
