package ua.cn.stu.oop.horus.core.domain.file;

import ua.cn.stu.oop.horus.core.domain.*;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;
import ua.cn.stu.oop.horus.core.domain.user.User;
import ua.cn.stu.oop.horus.core.domain.user.UserCarrier;

/**
 *
 * @author alex
 */
public class DBFileDirectory
        extends GenericDomain
        implements StringTitleCarrier,
        UserCarrier,
        ParentCarrier<DBFileDirectory> {

    private String title;
    private DBFileDirectory parent;
    private User user;

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public DBFileDirectory getParent() {
        return parent;
    }

    @Override
    public void setParent(DBFileDirectory parent) {
        this.parent = parent;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
