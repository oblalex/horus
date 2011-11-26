package ua.cn.stu.oop.horus.core.service.impl.gameflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow.UndeletableNodeCaptureEventDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.UndeletableNodeCaptureEvent;
import ua.cn.stu.oop.horus.core.domain.territory.Node;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.territory.NodeCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.gameflow.UndeletableNodeCaptureEventService;

/**
 *
 * @author alex
 */
@Service("undeletableNodeCaptureEventService")
public class UndeletableNodeCaptureEventServiceImpl
        extends GenericServiceImpl<UndeletableNodeCaptureEvent, UndeletableNodeCaptureEventDaoHibernateImpl>
        implements UndeletableNodeCaptureEventService {

    @Autowired
    public UndeletableNodeCaptureEventServiceImpl(UndeletableNodeCaptureEventDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public UndeletableNodeCaptureEvent getEntityOrNullByNode(Node node) {
        return NodeCarrierServiceImpl.getEntityOrNullByNodeFromDao(node, dao);
    }
}
