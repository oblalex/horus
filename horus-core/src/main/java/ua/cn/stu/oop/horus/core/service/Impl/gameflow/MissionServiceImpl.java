package ua.cn.stu.oop.horus.core.service.Impl.gameflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow.MissionDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.Mission;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.gameflow.MissionService;

/**
 *
 * @author alex
 */
@Service("missionService")
public class MissionServiceImpl
        extends GenericServiceImpl<Mission, MissionDaoHibernateImpl>
        implements MissionService {

    @Autowired
    public MissionServiceImpl(MissionDaoHibernateImpl dao) {
        super(dao);
    }
}
