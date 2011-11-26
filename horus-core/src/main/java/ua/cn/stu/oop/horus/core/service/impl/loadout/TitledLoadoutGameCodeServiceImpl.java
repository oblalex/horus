package ua.cn.stu.oop.horus.core.service.impl.loadout;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.loadout.TitledLoadoutGameCodeDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.domain.loadout.TitledLoadoutGameCode;
import ua.cn.stu.oop.horus.core.domain.text.TitleLink;
import ua.cn.stu.oop.horus.core.service.impl.GenericServiceImpl;
import ua.cn.stu.oop.horus.core.service.impl.text.TitleLinkCarrierServiceImpl;
import ua.cn.stu.oop.horus.core.service.loadout.TitledLoadoutGameCodeService;

/**
 *
 * @author alex
 */
@Service("titledLoadoutGameCode")
public class TitledLoadoutGameCodeServiceImpl
        extends GenericServiceImpl<TitledLoadoutGameCode, TitledLoadoutGameCodeDaoHibernateImpl>
        implements TitledLoadoutGameCodeService {

    @Autowired
    public TitledLoadoutGameCodeServiceImpl(TitledLoadoutGameCodeDaoHibernateImpl dao) {
        super(dao);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        return TitleLinkCarrierServiceImpl.getAllTitleLinksFromDao(dao);
    }

    @Override
    public TitledLoadoutGameCode getEntityOrNullByTitleLink(TitleLink titleLink) {
        return TitleLinkCarrierServiceImpl.getEntityOrNullByTitleLinkFromDao(titleLink, dao);
    }
}
