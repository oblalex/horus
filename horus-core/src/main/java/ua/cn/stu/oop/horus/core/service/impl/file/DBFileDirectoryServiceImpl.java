package ua.cn.stu.oop.horus.core.service.impl.file;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.ParentCarrierDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.file.DBFileDirectoryDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.file.DBFileDirectory;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.service.impl.*;
import ua.cn.stu.oop.horus.core.service.impl.text.StringTitleCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.user.UserCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.file.DBFileDirectoryService;

/**
 *
 * @author alex
 */
@Service("fileDirectoryService")
public class DBFileDirectoryServiceImpl
        extends GenericServiceImpl<DBFileDirectory, DBFileDirectoryDaoHibernateImpl>
        implements DBFileDirectoryService {

    public static final String USERS_HOME = "home";
    public static final String PICTURES = "pictures";
    
    @Autowired
    public DBFileDirectoryServiceImpl(DBFileDirectoryDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<DBFileDirectory> getEntitiesByUser(User user) {
        return (Collection<DBFileDirectory>) UserCarrierServiceImpl.getEntitiesByUserFromDao(user, dao);
    }

    @Override
    public Collection<String> getAllTitles() {
        return StringTitleCarrierServiceImpl.getAllTitlesFromDao(dao);
    }

    @Override
    public DBFileDirectory getEntityOrNullByTitle(String title) {
        return StringTitleCarrierServiceImpl.getEntityOrNullByTitleFromDao(title, dao);
    }

    @Override
    public Collection<DBFileDirectory> getChildrenOfParent(DBFileDirectory parent) {
        return (Collection<DBFileDirectory>) ParentCarrierServiceImpl.getChildrenOfParentFromDao(parent, (ParentCarrierDao)dao);
    }

    @Override
    public DBFileDirectory getUsersDirectory() {
        DBFileDirectory result = dao.getUsersDirectoryOrNull();
        if (result==null){
            result = createUsersDirectory();
        }
        return dao.getUsersDirectoryOrNull();
    }
    
    private DBFileDirectory createUsersDirectory(){
        DBFileDirectory result = new DBFileDirectory();
        result.setTitle(USERS_HOME);
        result.setParent(null);
        result.setUser(null);
        dao.saveAndGetId(result);
        return result;
    }

    @Override
    public DBFileDirectory getUserHomeDirectory(User user) {
        DBFileDirectory result = dao.getUserHomeDirectoryOrNull(user);
        if (result==null){
            result = createUserHomeDirectory(user);
        }
        return result;
    }

    private DBFileDirectory createUserHomeDirectory(User user) {
        DBFileDirectory usersDir = getUsersDirectory();
        DBFileDirectory userHome = new DBFileDirectory();
        
        userHome.setTitle(user.getLogin());
        userHome.setParent(usersDir);
        userHome.setUser(user);
        dao.saveAndGetId(userHome);
        
        return userHome;
    }
    
    @Override
    public DBFileDirectory getUserPicturesDirectory(User user) {
        DBFileDirectory result = dao.getUserPicturesDirectoryOrNull(user);
        
        if (result==null){
            result = createUserPicturesDirectory(user);
        }
        
        return result;
    }
    
    private DBFileDirectory createUserPicturesDirectory(User user) {
        DBFileDirectory userHome = getUserHomeDirectory(user);
        DBFileDirectory resust = new DBFileDirectory();
        
        resust.setTitle(PICTURES);
        resust.setParent(userHome);
        resust.setUser(user);
        dao.saveAndGetId(resust);
        
        return resust;
    }   
}
