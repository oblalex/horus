package ua.cn.stu.oop.horus.core.domain.user;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class UserBan
        extends GenericDomain
        implements UserCarrier{

    private User user;
    private User prescriber;
    private Timestamp startDate;
    private Timestamp expiryDate;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public User getPrescriber() {
        return prescriber;
    }

    public void setPrescriber(User prescriber) {
        this.prescriber = prescriber;
    }
}
