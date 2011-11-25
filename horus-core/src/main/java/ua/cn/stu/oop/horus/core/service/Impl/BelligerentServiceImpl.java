package ua.cn.stu.oop.horus.core.service.Impl;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.BelligerentDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.Belligerent;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.service.BelligerentService;
import ua.cn.stu.oop.horus.core.service.Impl.text.TitleLinkCarrierServiceImpl;

/**
 *
 * @author alex
 */
@Service("belligerentService")
public class BelligerentServiceImpl
        extends GenericServiceImpl<Belligerent, BelligerentDaoHibernateImpl>
        implements BelligerentService {

    @Autowired
    public BelligerentServiceImpl(BelligerentDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public Belligerent getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }
    
}
