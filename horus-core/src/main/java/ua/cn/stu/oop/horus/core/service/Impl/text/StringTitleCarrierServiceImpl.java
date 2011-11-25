package ua.cn.stu.oop.horus.core.service.Impl.text;

import java.util.Collection;
import ua.cn.stu.oop.horus.core.dao.text.StringTitleCarrierDao;
import ua.cn.stu.oop.horus.core.domain.text.StringTitleCarrier;

/**
 *
 * @author alex
 */
public class StringTitleCarrierServiceImpl {
    
    public static Collection<String> getAllTitlesFromDao(
            StringTitleCarrierDao<? extends StringTitleCarrier> dao) {
        return dao.getAllTitles();
    }

    public static <E extends StringTitleCarrier> E getEntityOrNullByTitleFromDao(
            String title,
            StringTitleCarrierDao<E> dao) {
        return dao.getEntityOrNullByTitle(title);
    }
}
