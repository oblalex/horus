package ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.gameflow.SortieDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.LoadoutCarrierQueries;
import ua.cn.stu.oop.horus.core.domain.gameflow.Sortie;
import ua.cn.stu.oop.horus.core.domain.loadout.LoadoutEntity;

/**
 *
 * @author alex
 */
@Repository("SortieDaoHibernateImpl")
public class SortieDaoHibernateImpl
        extends GenericDaoHibernateImpl<Sortie>
        implements SortieDao {

    public SortieDaoHibernateImpl() {
        super(Sortie.class);
    }

    @Override
    public Collection<Sortie> getEntitiesByLoadout(LoadoutEntity loadoutEntity) {
        Class cls = getEntityClass();
        String query = LoadoutCarrierQueries.
                getCarrierByLoadout(cls);
        return multipleResultByQuery(query, loadoutEntity);
    }
}
