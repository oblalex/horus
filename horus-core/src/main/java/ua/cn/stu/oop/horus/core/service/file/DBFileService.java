package ua.cn.stu.oop.horus.core.service.file;

import ua.cn.stu.oop.horus.core.service.GenericService;
import ua.cn.stu.oop.horus.core.service.text.StringTitleCarrierService;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.service.user.UserCarrierService;

/**
 *
 * @author alex
 */
public interface DBFileService
        extends GenericService<DBFile>,
        UserCarrierService<DBFile>,
        StringTitleCarrierService<DBFile> {
}
