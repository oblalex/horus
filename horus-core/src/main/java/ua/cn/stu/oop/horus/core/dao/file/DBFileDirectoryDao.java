package ua.cn.stu.oop.horus.core.dao.file;

import ua.cn.stu.oop.horus.core.dao.text.StringTitleCarrierDao;
import ua.cn.stu.oop.horus.core.dao.*;
import ua.cn.stu.oop.horus.core.dao.user.UserCarrierDao;
import ua.cn.stu.oop.horus.core.domain.file.DBFileDirectory;
import ua.cn.stu.oop.horus.core.domain.user.User;

/**
 *
 * @author alex
 */
public interface DBFileDirectoryDao
        extends GenericDao<DBFileDirectory>,
        UserCarrierDao<DBFileDirectory>,
        StringTitleCarrierDao<DBFileDirectory>,
        ParentCarrierDao<DBFileDirectory> {

    public DBFileDirectory getUsersDirectoryOrNull();
    public DBFileDirectory getUserHomeDirectoryOrNull(User user);
    public DBFileDirectory getUserPicturesDirectoryOrNull(User user);
}
