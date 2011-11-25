package ua.cn.stu.oop.horus.core.dao.user.group.air;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.StringTitleCarrierDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.dao.user.group.GroupLinkCarrierDao;
import ua.cn.stu.oop.horus.core.domain.user.group.air.AirGroup;

/**
 *
 * @author alex
 */
public interface AirGroupDao
        extends GenericDao<AirGroup>,
        TypeCarrierDao<AirGroup>,
        StringTitleCarrierDao<AirGroup>,
        GroupLinkCarrierDao<AirGroup>{
    
}
