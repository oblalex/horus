package ua.cn.stu.oop.horus.core.service.text;

import ua.cn.stu.oop.horus.core.domain.text.LocalizedData;
import ua.cn.stu.oop.horus.core.domain.text.LocalizedTitle;
import ua.cn.stu.oop.horus.core.service.GenericService;

/**
 *
 * @author alex
 */
public interface LocalizedDataService
        extends GenericService<LocalizedData> {
    public LocalizedData getByLocalizedTitle(LocalizedTitle localizedTitle);
}
