package ua.cn.stu.oop.horus.core.dao.gameflow;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.gameflow.MissionEvent;

/**
 *
 * @author alex
 */
public interface MissionEventDao
        extends GenericDao<MissionEvent>,
        TypeCarrierDao<MissionEvent> {
}
