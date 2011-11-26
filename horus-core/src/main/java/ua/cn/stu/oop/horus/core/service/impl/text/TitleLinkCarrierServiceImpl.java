package ua.cn.stu.oop.horus.core.service.impl.text;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public class TitleLinkCarrierServiceImpl {

    public static Collection<TitleLink> getAllTitleLinksFromDao(
            TitleLinkCarrierDao<? extends TitleLinkCarrier> dao) {
        return dao.getAllTitleLinks();
    }

    public static <E extends TitleLinkCarrier> E getEntityOrNullByTitleLinkFromDao(
            TitleLink titleLink,
            TitleLinkCarrierDao<E> dao) {
        return dao.getEntityOrNullByTitleLink(titleLink);
    }
}
