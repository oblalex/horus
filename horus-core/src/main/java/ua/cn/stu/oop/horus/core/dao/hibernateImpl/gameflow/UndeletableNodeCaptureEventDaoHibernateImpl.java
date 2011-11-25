package ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.gameflow.UndeletableNodeCaptureEventDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.NodeCarrierQueries;
import ua.cn.stu.oop.horus.core.domain.gameflow.UndeletableNodeCaptureEvent;
import ua.cn.stu.oop.horus.core.domain.territory.Node;

/**
 *
 * @author alex
 */
@Repository("UndeletableNodeCaptureEventDaoHibernateImpl")
public class UndeletableNodeCaptureEventDaoHibernateImpl
        extends GenericDaoHibernateImpl<UndeletableNodeCaptureEvent>
        implements UndeletableNodeCaptureEventDao {

    public UndeletableNodeCaptureEventDaoHibernateImpl() {
        super(UndeletableNodeCaptureEvent.class);
    }

    @Override
    public UndeletableNodeCaptureEvent getEntityOrNullByNode(Node node) {
        Class cls = getEntityClass();
        String query = NodeCarrierQueries.
                getCarriersByNode(cls);
        return (UndeletableNodeCaptureEvent)
                singleResultOrNullByQuery(query, node);
    }
}
