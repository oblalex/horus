package ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.gameflow.MissionDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.Mission;

/**
 *
 * @author alex
 */
@Repository("MissionDaoHibernateImpl")
public class MissionDaoHibernateImpl
        extends GenericDaoHibernateImpl<Mission>
        implements MissionDao {

    public MissionDaoHibernateImpl() {
        super(Mission.class);
    }
}
