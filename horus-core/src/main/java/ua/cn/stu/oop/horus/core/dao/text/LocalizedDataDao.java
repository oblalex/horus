package ua.cn.stu.oop.horus.core.dao.text;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public interface LocalizedDataDao
        extends GenericDao<LocalizedData> {
    public LocalizedData getByLocalizedTitle(LocalizedTitle localizedTitle);
}
