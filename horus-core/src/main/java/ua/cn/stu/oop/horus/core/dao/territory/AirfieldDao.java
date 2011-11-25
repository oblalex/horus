package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.Airfield;

/**
 *
 * @author alex
 */
public interface AirfieldDao
        extends GenericDao<Airfield>,
        TitleLinkCarrierDao<Airfield>,
        NodeCarrierDao<Airfield>,
        TypeCarrierDao<Airfield> {
}
