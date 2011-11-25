package ua.cn.stu.oop.horus.core.dao.type;

import ua.cn.stu.oop.horus.core.dao.*;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.type.Type;

/**
 *
 * @author alex
 */
public interface TypeDao 
        extends GenericDao<Type>,
        TitleLinkCarrierDao<Type>,
        TypeCarrierDao<Type>,
        ParentCarrierDao<Type>{
    
}
