package ua.cn.stu.oop.horus.core.dao.hibernateImpl.file;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.file.DBFileDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.domain.file.DBFile;
import ua.cn.stu.oop.horus.core.domain.user.User;

/**
 *
 * @author alex
 */
@Repository("FileDaoHibernateImpl")
public class DBFileDaoHibernateImpl 
        extends GenericDaoHibernateImpl<DBFile>
        implements DBFileDao {

    public DBFileDaoHibernateImpl(){
        super(DBFile.class);
    }

    @Override
    public Collection<String> getAllTitles() {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getAllTitles(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public DBFile getEntityOrNullByTitle(String title) {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getTitleCarriersByTitle(cls);
        return (DBFile) singleResultOrNullByQuery(query,title);
    }

    @Override
    public Collection<DBFile> getEntitiesByUser(User user) {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getCarriersByUser(cls);
        return multipleResultByQuery(query, user);
    }

    @Override
    public Collection<DBFile> getAllSortedByUserLogin() {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getAllSortedByUserLogin(cls);
        return multipleResultByQuery(query);
    }


    
}
