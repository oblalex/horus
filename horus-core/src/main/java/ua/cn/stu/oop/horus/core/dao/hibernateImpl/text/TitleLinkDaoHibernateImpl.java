package ua.cn.stu.oop.horus.core.dao.hibernateImpl.text;

import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkDao;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;

/**
 *
 * @author alex
 */
@Repository("TitleLinkDaoHibernateImpl")
public class TitleLinkDaoHibernateImpl
        extends GenericDaoHibernateImpl<TitleLink>
        implements TitleLinkDao {

    public TitleLinkDaoHibernateImpl() {
        super(TitleLink.class);
    }
}
