package ua.cn.stu.oop.horus.core.service.impl.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.text.TitleLinkDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkService;

/**
 *
 * @author alex
 */
@Service("titleLinkService")
public class TitleLinkServiceImpl
        extends GenericServiceImpl<TitleLink, TitleLinkDaoHibernateImpl>
        implements TitleLinkService {

    @Autowired
    public TitleLinkServiceImpl(TitleLinkDaoHibernateImpl dao) {
        super(dao);
    }
}
