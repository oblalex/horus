package ua.cn.stu.oop.horus.core.service.impl.gameflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow.MissionBlockDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.MissionBlock;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
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
