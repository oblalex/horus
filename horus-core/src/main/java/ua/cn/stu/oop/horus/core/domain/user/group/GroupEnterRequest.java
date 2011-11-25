package ua.cn.stu.oop.horus.core.domain.user.group;

import java.sql.Timestamp;
import ua.cn.stu.oop.horus.core.domain.user.*;
import ua.cn.stu.oop.horus.core.domain.type.*;
import ua.cn.stu.oop.horus.core.domain.GenericDomain;

/**
 *
 * @author alex
 */
public class GroupEnterRequest
        extends GenericDomain
        implements GroupLinkCarrier,
        TypeCarrier<GroupEnterRequest.GroupEnterRequestType>,
        UserCarrier{

    private GroupLink groupLink;
    private User user;
    private String authorComment;
    private Timestamp creationRealDate;
    private Timestamp creationGameDate;
    private GroupEnterRequestType type;
    private boolean considered;

    public static enum GroupEnterRequestType implements EntityType {

        APPLICATION,
        INVITATION
    }

    public boolean isConsidered() {
        return considered;
    }

    public void setConsidered(boolean considered) {
        this.considered = considered;
    }

    public String getAuthorComment() {
        return authorComment;
    }

    public void setAuthorComment(String authorComment) {
        this.authorComment = authorComment;
    }

    @Override
    public GroupLink getGroupLink() {
        return groupLink;
    }

    @Override
    public void setGroupLink(GroupLink groupLink) {
        this.groupLink = groupLink;
    }

    @Override
    public GroupEnterRequestType getType() {
        return type;
    }

    @Override
    public void setType(GroupEnterRequestType type) {
        this.type = type;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreationGameDate() {
        return creationGameDate;
    }

    public void setCreationGameDate(Timestamp creationGameDate) {
        this.creationGameDate = creationGameDate;
    }

    public Timestamp getCreationRealDate() {
        return creationRealDate;
    }

    public void setCreationRealDate(Timestamp creationRealDate) {
        this.creationRealDate = creationRealDate;
    }
}
