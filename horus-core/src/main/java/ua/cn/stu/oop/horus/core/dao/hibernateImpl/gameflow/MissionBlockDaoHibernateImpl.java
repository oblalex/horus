package ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.gameflow.MissionBlockDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.MissionBlock;

/**
 *
 * @author alex
 */
@Repository("MissionBlockDaoHibernateImpl")
public class MissionBlockDaoHibernateImpl
        extends GenericDaoHibernateImpl<MissionBlock>
        implements MissionBlockDao {

    public MissionBlockDaoHibernateImpl() {
        super(MissionBlock.class);
    }
}
