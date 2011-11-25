package ua.cn.stu.oop.horus.core.service.Impl.file;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.file.DBFileDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.service.Impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.text.StringTitleCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.Impl.user.UserCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.file.DBFileService;

/**
 *
 * @author alex
 */
@Service("fileService")
public class DBFileServiceImpl
        extends GenericServiceImpl<DBFile, DBFileDaoHibernateImpl>
        implements DBFileService {

    @Autowired
    public DBFileServiceImpl(DBFileDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<DBFile> getEntitiesByUser(User user) {
        return (Collection<DBFile>) UserCarrierServiceImpl.getEntitiesByUserFromDao(user, dao);
    }

    @Override
    public Collection<String> getAllTitles() {
        return StringTitleCarrierServiceImpl.getAllTitlesFromDao(dao);
    }

    @Override
    public DBFile getEntityOrNullByTitle(String title) {
        return StringTitleCarrierServiceImpl.getEntityOrNullByTitleFromDao(title, dao);
    }
}
