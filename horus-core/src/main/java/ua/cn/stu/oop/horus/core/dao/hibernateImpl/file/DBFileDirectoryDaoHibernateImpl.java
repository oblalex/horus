package ua.cn.stu.oop.horus.core.dao.hibernateImpl.file;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.file.DBFileDirectoryDao;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.domain.file.DBFileDirectory;
import ua.cn.stu.oop.horus.core.domain.user.User;

/**
 *
 * @author alex
 */
@Repository("FileDirectoryDaoHibernateImpl")
public class DBFileDirectoryDaoHibernateImpl
        extends GenericDaoHibernateImpl<DBFileDirectory>
        implements DBFileDirectoryDao {

    public DBFileDirectoryDaoHibernateImpl() {
        super(DBFileDirectory.class);
    }

    @Override
    public Collection<String> getAllTitles() {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getAllTitles(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public DBFileDirectory getEntityOrNullByTitle(String title) {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getTitleCarriersByTitle(cls, title);
        return (DBFileDirectory) singleResultOrNullByQuery(query);
    }

    @Override
    public Collection<DBFileDirectory> getChildrenOfParent(DBFileDirectory parent) {
        Class cls = getEntityClass();
        String query = ParentCarrierQueries.
                getChildrenByParent(cls);
        String paramTitle = 
                ParentCarrierQueries.PARAMETER_PARENT;
        return multipleResultByQueryWithNamedParam(query, paramTitle, parent);
    }

    @Override
    public Collection<DBFileDirectory> getEntitiesByUser(User user) {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getCarriersByUser(cls);
        return multipleResultByQuery(query, user);
    }

    @Override
    public DBFileDirectory getUsersDirectoryOrNull() {
        return (DBFileDirectory) singleResultOrNullByNamedQuery(
                "getUsersDirectoryOrNullNativeSQL");
    }

    @Override
    public DBFileDirectory getUserHomeDirectoryOrNull(User user) {
        return (DBFileDirectory) singleResultOrNullByNamedQuery(
                "getUserHomeDirectoryOrNullHQL", user);
    }

    @Override
    public DBFileDirectory getUserPicturesDirectoryOrNull(User user) {
        return (DBFileDirectory) singleResultOrNullByNamedQuery(
                "getUserPicturesDirectoryOrNullHQL", user);
    }

    @Override
    public Collection<DBFileDirectory> getAllSortedByUserLogin() {
        Class cls = getEntityClass();
        String query = UserCarrierQueries.
                getAllSortedByUserLogin(cls);
        return multipleResultByQuery(query);
    }
}
