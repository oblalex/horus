package ua.cn.stu.oop.horus.core.service.Impl.text.report;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.text.report.LocalizedChangesDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.report.LocalizedChanges;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.text.StringTitleCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.text.report.LocalizedChangesService;

/**
 *
 * @author alex
 */
@Service("localizedChangesService")
public class LocalizedChangesServiceImpl
        extends GenericServiceImpl<LocalizedChanges, LocalizedChangesDaoHibernateImpl>
        implements LocalizedChangesService {

    @Autowired
    public LocalizedChangesServiceImpl(LocalizedChangesDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<String> getAllTitles() {
        return StringTitleCarrierServiceImpl.getAllTitlesFromDao(dao);
    }

    @Override
    public LocalizedChanges getEntityOrNullByTitle(String title) {
        return StringTitleCarrierServiceImpl.getEntityOrNullByTitleFromDao(title, dao);
    }
}
