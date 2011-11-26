package ua.cn.stu.oop.horus.core.service.impl.gameflow;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow.SortieDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.Sortie;
import ua.cn.stu.oop.horus.core.domain.loadout.LoadoutEntity;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.loadout.LoadoutCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.gameflow.SortieService;

/**
 *
 * @author alex
 */
@Service("SortieService")
public class SortieServiceImpl
        extends GenericServiceImpl<Sortie, SortieDaoHibernateImpl>
        implements SortieService {

    @Autowired
    public SortieServiceImpl(SortieDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<Sortie> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        return (Collection<Sortie>) LoadoutCarrierServiceImpl.getEntitiesByLoadoutFromDao(loadoutEntity, dao);
    }
}
