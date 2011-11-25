package ua.cn.stu.oop.horus.core.service.Impl.gameflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow.MissionBlockDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.MissionBlock;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.gameflow.MissionBlockService;

/**
 *
 * @author alex
 */
@Service("missionBlockService")
public class MissionBlockServiceImpl
        extends GenericServiceImpl<MissionBlock, MissionBlockDaoHibernateImpl>
        implements MissionBlockService {

    @Autowired
    public MissionBlockServiceImpl(MissionBlockDaoHibernateImpl dao) {
        super(dao);
    }
}
