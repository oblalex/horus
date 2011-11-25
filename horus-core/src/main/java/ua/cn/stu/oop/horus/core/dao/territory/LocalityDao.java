package ua.cn.stu.oop.horus.core.dao.territory;

import ua.cn.stu.oop.horus.core.dao.GenericDao;
import ua.cn.stu.oop.horus.core.dao.text.TitleLinkCarrierDao;
import ua.cn.stu.oop.horus.core.dao.type.TypeCarrierDao;
import ua.cn.stu.oop.horus.core.domain.territory.Locality;

/**
 *
 * @author alex
 */
public interface LocalityDao
        extends GenericDao<Locality>,
        TitleLinkCarrierDao<Locality>,
        NodeCarrierDao<Locality>,
        TypeCarrierDao<Locality> {
}
