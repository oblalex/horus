package ua.cn.stu.oop.horus.core.service.text;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;

/**
 *
 * @author alex
 */
public interface StringTitleCarrierService<E extends StringTitleCarrier> {

    public Collection<String> getAllTitles();

    public E getEntityOrNullByTitle(String title);
}
