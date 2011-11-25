package ua.cn.stu.oop.horus.core.service.Impl.loadout;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.ParentCarrierDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout.LoadoutDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.loadout.Loadout;
import ua.cn.stu.oop.horus.core.service.Impl.*;
import ua.cn.stu.oop.horus.core.service.loadout.LoadoutService;

/**
 *
 * @author alex
 */
@Service("loadoutService")
public class LoadoutServiceImpl
        extends GenericServiceImpl<Loadout, LoadoutDaoHibernateImpl>
        implements LoadoutService {

    @Autowired
    public LoadoutServiceImpl(LoadoutDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<Loadout> getChildrenOfParent(Loadout parent) {
        return (Collection<Loadout>) ParentCarrierServiceImpl.getChildrenOfParentFromDao(parent, (ParentCarrierDao)dao);
    }
}
