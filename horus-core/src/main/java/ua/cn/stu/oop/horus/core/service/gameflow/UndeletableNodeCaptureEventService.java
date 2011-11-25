package ua.cn.stu.oop.horus.core.service.gameflow;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.territory.NodeCarrierService;
import ua.cn.stu.oop.horus.core.domain.gameflow.UndeletableNodeCaptureEvent;

/**
 *
 * @author alex
 */
public interface UndeletableNodeCaptureEventService
        extends GenericService<UndeletableNodeCaptureEvent>,
        NodeCarrierService<UndeletableNodeCaptureEvent> {
}
