package ua.cn.stu.oop.horus.core.service.text.report;

import ua.cn.stu.oop.horus.core.domain.text.report.LocalizedChanges;
import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.StringTitleCarrierService;

/**
 *
 * @author alex
 */
public interface LocalizedChangesService
        extends GenericService<LocalizedChanges>,
        StringTitleCarrierService<LocalizedChanges> {
}
