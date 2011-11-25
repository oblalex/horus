package ua.cn.stu.oop.horus.core.dao.gameflow;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.territory.NodeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.gameflow.UndeletableNodeCaptureEvent;

/**
 *
 * @author alex
 */
public interface UndeletableNodeCaptureEventDao
        extends GenericDao<UndeletableNodeCaptureEvent>,
        NodeCarrierDao<UndeletableNodeCaptureEvent> {
}
