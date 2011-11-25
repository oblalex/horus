package ua.cn.stu.oop.horus.core.domain.file;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.UserCarrier;

/**
 *
 * @author alex
 */
public class DBFile
        extends GenericDomain
        implements StringTitleCarrier,
        UserCarrier{

    private DBFileDirectory directory;
    private String title;
    private String notes;
    private String contentType;
    private Timestamp lastModificationTime;
    private User user;
    private byte[] data;

    public DBFileDirectory getDirectory() {
        return directory;
    }

    public void setDirectory(DBFileDirectory directory) {
        this.directory = directory;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Timestamp getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Timestamp lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
