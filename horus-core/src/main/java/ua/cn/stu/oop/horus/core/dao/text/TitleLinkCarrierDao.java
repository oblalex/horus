package ua.cn.stu.oop.horus.core.dao.text;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.text.*;

/**
 *
 * @author alex
 */
public interface TitleLinkCarrierDao<E extends TitleLinkCarrier> {

    public Collection<TitleLink> getAllTitleLinks();

    public E getEntityOrNullByTitleLink(TitleLink titleLink);
}
