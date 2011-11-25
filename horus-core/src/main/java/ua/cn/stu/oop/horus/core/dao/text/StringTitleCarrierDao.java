package ua.cn.stu.oop.horus.core.dao.text;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;

/**
 *
 * @author alex
 */
public interface StringTitleCarrierDao<E extends StringTitleCarrier>{

    public Collection<String> getAllTitles();

    public E getEntityOrNullByTitle(String title);
}
