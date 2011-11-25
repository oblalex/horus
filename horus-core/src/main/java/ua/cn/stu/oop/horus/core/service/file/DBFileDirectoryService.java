package ua.cn.stu.oop.horus.core.service.file;

import ua.cn.stu.oop.horus.core.service.text.StringTitleCarrierService;
import ua.cn.stu.oop.horus.core.service.*;
import ua.cn.stu.oop.horus.core.domain.file.DBFileDirectory;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.service.user.UserCarrierService;

/**
 *
 * @author alex
 */
public interface DBFileDirectoryService
        extends GenericService<DBFileDirectory>,
        UserCarrierService<DBFileDirectory>,
        StringTitleCarrierService<DBFileDirectory>,
        ParentCarrierService<DBFileDirectory> {

    public DBFileDirectory getUsersDirectory();
    public DBFileDirectory getUserHomeDirectory(User user);
    public DBFileDirectory getUserPicturesDirectory(User user);    
}
