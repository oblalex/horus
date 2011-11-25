package ua.cn.stu.oop.horus.core.dao.text.report;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.StringTitleCarrierDao;
import ua.cn.stu.oop.horus.core.domain.text.report.LocalizedChanges;

/**
 *
 * @author alex
 */
public interface LocalizedChangesDao
        extends GenericDao<LocalizedChanges>,
        StringTitleCarrierDao<LocalizedChanges> {
}
