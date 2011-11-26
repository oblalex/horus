package ua.cn.stu.oop.horus.core.service.impl.gameflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.gameflow.WarDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.gameflow.War;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.gameflow.WarService;

/**
 *
 * @author alex
 */
@Service("warService")
public class WarServiceImpl
        extends GenericServiceImpl<War, WarDaoHibernateImpl>
        implements WarService {

    @Autowired
    public WarServiceImpl(WarDaoHibernateImpl dao) {
        super(dao);
    }
}
