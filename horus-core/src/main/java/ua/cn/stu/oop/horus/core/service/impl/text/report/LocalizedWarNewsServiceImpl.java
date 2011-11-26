package ua.cn.stu.oop.horus.core.service.impl.text.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.text.report.LocalizedWarNewsDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.report.LocalizedWarNews;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.text.report.LocalizedWarNewsService;

/**
 *
 * @author alex
 */
@Service("localizedWarNewsService")
public class LocalizedWarNewsServiceImpl
        extends GenericServiceImpl<LocalizedWarNews, LocalizedWarNewsDaoHibernateImpl>
        implements LocalizedWarNewsService {

    @Autowired
    public LocalizedWarNewsServiceImpl(LocalizedWarNewsDaoHibernateImpl dao) {
        super(dao);
    }
    
}
