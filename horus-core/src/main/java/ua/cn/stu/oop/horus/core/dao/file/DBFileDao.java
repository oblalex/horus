package ua.cn.stu.oop.horus.core.dao.file;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.StringTitleCarrierDao;
import ua.cn.stu.oop.horus.core.dao.user.UserCarrierDao;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;

/**
 *
 * @author alex
 */
public interface DBFileDao
        extends GenericDao<DBFile>,
        UserCarrierDao<DBFile>,
        StringTitleCarrierDao<DBFile> {
}
