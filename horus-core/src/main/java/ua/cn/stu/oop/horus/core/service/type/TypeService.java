package ua.cn.stu.oop.horus.core.service.type;

import ua.cn.stu.oop.horus.core.domain.type.Type;
import ua.cn.stu.oop.horus.core.service.*;
import ua.cn.stu.oop.horus.core.service.text.TitleLinkCarrierService;

/**
 *
 * @author alex
 */
public interface TypeService
        extends GenericService<Type>,
        TitleLinkCarrierService<Type>,
        TypeCarrierService<Type>,
        ParentCarrierService<Type> {
}
