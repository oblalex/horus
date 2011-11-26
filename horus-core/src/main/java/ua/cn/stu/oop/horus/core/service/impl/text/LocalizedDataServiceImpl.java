package ua.cn.stu.oop.horus.core.service.impl.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.text.LocalizedDataDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.text.LocalizedDataService;

/**
 *
 * @author alex
 */
@Service("localizedDataService")
public class LocalizedDataServiceImpl
        extends GenericServiceImpl<LocalizedData, LocalizedDataDaoHibernateImpl>
        implements LocalizedDataService {

    @Autowired
    public LocalizedDataServiceImpl(LocalizedDataDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public LocalizedData getByLocalizedTitle(LocalizedTitle localizedTitle) {
        return dao.getByLocalizedTitle(localizedTitle);
    }
}
