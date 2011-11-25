package ua.cn.stu.oop.horus.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.file.DBFileDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.file.DBFileDirectoryDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.file.DBFileDirectory;
import ua.cn.stu.oop.horus.core.service.file.DBFileDirectoryService;

/**
 *
 * @author alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring/coreContext.xml"})
public class FileDaoTest extends AbstractJUnit38SpringContextTests {

    @Autowired
    DBFileDirectoryService fileDirectoryService;
    
    private DBFileDirectoryDaoHibernateImpl fileDirDao;
    private DBFileDaoHibernateImpl fileDao;

    @Autowired
    public void setFileDirDao(DBFileDirectoryDaoHibernateImpl fileDirDao) {
        this.fileDirDao = fileDirDao;
    }

    @Autowired
    public void setFileDao(DBFileDaoHibernateImpl fileDao) {
        this.fileDao = fileDao;
    }

    public DBFileDirectory getRootDir() {

        String root = "root";

        DBFileDirectory fdir = fileDirDao.getEntityOrNullByTitle(root);
        if (fdir != null) {
            return fdir;
        }

        fdir = new DBFileDirectory();
        fdir.setTitle(root);
        fdir.setParent(null);
        fileDirDao.saveAndGetId(fdir);
        return fdir;
    }

    @Test
    public void addFileTest() {
        
//       if (getRootDir()==null) return;
//        
//        String fname = "test.png";
//
//        File dbf = fileDao.getEntityByTitleOrNull(fname);
//        if (dbf != null) {
//            return;
//        }
//
//        String fpath = "/home/alex/";
//        dbf = new File();
//        dbf.setDirectory(getRootDir());
//        dbf.setNotes("null");
//        dbf.setTitle(fname);
//        dbf.setContentType("image/png");
//        dbf.setLastModificationTime(new Timestamp(System.currentTimeMillis()));
//
//        java.io.File fsfile = new java.io.File(fpath + fname);
//        byte[] fileBArray = new byte[(int) fsfile.length()];
//        FileInputStream fis;
//        
//        try {
//            fis = new FileInputStream(fsfile);
//            try {
//                fis.read(fileBArray);
//            } catch (IOException ex) {
//                Logger.getLogger(FileDaoTest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FileDaoTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        dbf.setData(fileBArray);
//        fileDao.createAndGetId(dbf);
    }
}
